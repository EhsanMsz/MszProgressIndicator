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

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction2Step
import com.ehsanmsz.mszprogressindicator.fraction2StepReversed

private const val DefaultAnimationDuration = 500
private const val DefaultAnimationDelay = 500
private const val DefaultBallCount = 3

private val DefaultMinBallDiameter = 8.dp
private val DefaultMaxBallDiameter = 12.dp
private val DefaultMinSpacing = 4.dp
private val DefaultMaxSpacing = 8.dp

@Composable
fun BallRotateProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    animationDelay: Int = DefaultAnimationDelay,
    ballCount: Int = DefaultBallCount,
    minBallDiameter: Dp = DefaultMinBallDiameter,
    maxBallDiameter: Dp = DefaultMaxBallDiameter,
    minSpacing: Dp = DefaultMinSpacing,
    maxSpacing: Dp = DefaultMaxSpacing
) {
    val transition = rememberInfiniteTransition()

    //Fractional diameter
    val diameter by transition.fraction2StepReversed(
        durationMillis = animationDuration,
        delayMillis = animationDelay,
        easing = FastOutSlowInEasing
    )

    //Fractional rotation
    val rotation by transition.fraction2Step(
        durationMillis = animationDuration,
        delayMillis = animationDelay,
        easing = FastOutSlowInEasing
    )

    val maxDiameter = (maxBallDiameter + maxSpacing) * ballCount - maxSpacing

    ProgressIndicator(modifier, maxDiameter) {
        drawIndeterminateBallRotateIndicator(
            diameter = lerp(minBallDiameter, maxBallDiameter, diameter).toPx(),
            spacing = lerp(minSpacing, maxSpacing, diameter).toPx(),
            ballCount = ballCount,
            rotationDegrees = rotation * 360f,
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallRotateIndicator(
    diameter: Float,
    spacing: Float,
    ballCount: Int,
    rotationDegrees: Float,
    color: Color
) {

    rotate(
        degrees = rotationDegrees
    ) {
        val radius = diameter / 2
        val offset = diameter + spacing
        val minWidthOffset = (size.width - (offset * ballCount - spacing)) / 2

        for (i in 0 until ballCount) {
            val x = radius + offset * i + minWidthOffset
            drawCircle(
                color = color,
                radius = radius,
                center = Offset(x, size.height / 2)
            )
        }
    }
}
