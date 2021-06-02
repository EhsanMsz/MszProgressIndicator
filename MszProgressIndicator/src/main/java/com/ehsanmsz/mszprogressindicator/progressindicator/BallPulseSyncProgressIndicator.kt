/*
 * Copyright 2021 Ehsan Msz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ehsanmsz.mszprogressindicator.progressindicator

import androidx.compose.animation.core.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator

private const val DefaultAnimationDuration = 500
private const val DefaultAnimationDelay = 130
private const val DefaultStartDelay = 0
private const val DefaultBallCount = 3

private val DefaultBallDiameterMax = 14.dp
private val DefaultSpacing = 4.dp
private val DefaultBallJumpHeight = 12.dp

@Composable
fun BallPulseSyncProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    animationDelay: Int = DefaultAnimationDelay,
    startDelay: Int = DefaultStartDelay,
    ballCount: Int = DefaultBallCount,
    ballDiameter: Dp = DefaultBallDiameterMax,
    ballJumpHeight: Dp = DefaultBallJumpHeight,
    spacing: Dp = DefaultSpacing
) {
    val transition = rememberInfiniteTransition()

    val duration = startDelay + animationDuration + animationDelay

    //Fractional jump height
    val jumpHeight = arrayListOf<Float>().apply {
        for (i in 0 until ballCount) {
            val delay = startDelay + animationDelay / (ballCount - 1) * i
            val height by transition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = duration
                        0f at delay with FastOutSlowInEasing
                        1f at (animationDuration / 2) + delay with FastOutSlowInEasing
                        0f at animationDuration + delay
                        0f at duration
                    },
                )
            )
            add(height)
        }
    }

    val width = (ballDiameter + spacing) * ballCount - spacing
    val height = ballJumpHeight + ballDiameter

    ProgressIndicator(modifier, width, height) {
        drawIndeterminateBallPulseSyncIndicator(
            maxDiameter = ballDiameter.toPx(),
            jumpHeight = jumpHeight.map { lerp(0.dp, ballJumpHeight, it).toPx() },
            spacing = spacing.toPx(),
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallPulseSyncIndicator(
    maxDiameter: Float,
    jumpHeight: List<Float>,
    spacing: Float,
    color: Color
) {
    val y = size.height - (maxDiameter / 2)

    for (i in jumpHeight.indices) {
        val x = (maxDiameter + spacing) * i + maxDiameter / 2
        drawCircle(
            color = color,
            radius = maxDiameter / 2,
            center = Offset(x, y - jumpHeight[i])
        )
    }
}
