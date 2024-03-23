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
import androidx.compose.material3.MaterialTheme
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
private const val DefaultAnimationDelay = 200
private const val DefaultStartDelay = 0
private const val DefaultBallCount = 3

private val DefaultMaxBallDiameter = 14.dp
private val DefaultMinBallDiameter = 4.dp
private val DefaultSpacing = 4.dp

@Composable
fun BallPulseProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    animationDelay: Int = DefaultAnimationDelay,
    startDelay: Int = DefaultStartDelay,
    ballCount: Int = DefaultBallCount,
    maxBallDiameter: Dp = DefaultMaxBallDiameter,
    minBallDiameter: Dp = DefaultMinBallDiameter,
    spacing: Dp = DefaultSpacing
) {
    val transition = rememberInfiniteTransition()

    val duration = startDelay + animationDuration + animationDelay

    //Fractional diameter
    val diameters = arrayListOf<Float>().apply {
        for (i in 0 until ballCount) {
            val delay = startDelay + (animationDelay / (ballCount - 1)) * i
            val diameter by transition.animateFloat(
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
            add(diameter)
        }
    }

    val width = (maxBallDiameter + spacing) * ballCount - spacing

    ProgressIndicator(modifier, width, maxBallDiameter) {
        drawIndeterminateBallPulseIndicator(
            maxDiameter = maxBallDiameter.toPx(),
            diameter = diameters.map { lerp(minBallDiameter, maxBallDiameter, it).toPx() },
            spacing = spacing.toPx(),
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallPulseIndicator(
    maxDiameter: Float,
    diameter: List<Float>,
    spacing: Float,
    color: Color
) {
    for (i in diameter.indices) {
        val x = i * (maxDiameter + spacing) + maxDiameter / 2
        drawCircle(
            color = color,
            radius = diameter[i] / 2,
            center = Offset(x, size.height / 2)
        )
    }
}
