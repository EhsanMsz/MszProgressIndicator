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

import androidx.compose.animation.core.rememberInfiniteTransition
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
import com.ehsanmsz.mszprogressindicator.fraction2StepReversed
import com.ehsanmsz.mszprogressindicator.lerp

private const val DefaultAnimationDuration = 700

private const val DefaultMinAlpha = .5f
private const val DefaultMaxAlpha = 1f
private const val DefaultBallCount = 3

private val DefaultMinBallDiameter = 8.dp
private val DefaultMaxBallDiameter = 14.dp
private val DefaultSpacing = 4.dp

@Composable
fun BallBeatProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    ballCount: Int = DefaultBallCount,
    minAlpha: Float = DefaultMinAlpha,
    maxAlpha: Float = DefaultMaxAlpha,
    minBallDiameter: Dp = DefaultMinBallDiameter,
    maxBallDiameter: Dp = DefaultMaxBallDiameter,
    spacing: Dp = DefaultSpacing
) {
    val transition = rememberInfiniteTransition()

    val fraction by transition.fraction2StepReversed(animationDuration)

    val maxWidth = maxBallDiameter * ballCount + spacing * (ballCount - 1)

    ProgressIndicator(modifier, maxWidth, maxBallDiameter) {
        val diameter = listOf(
            lerp(minBallDiameter, maxBallDiameter, fraction).toPx(),
            lerp(minBallDiameter, maxBallDiameter, 1 - fraction).toPx()
        )

        val alpha = listOf(
            lerp(minAlpha, maxAlpha, fraction),
            lerp(minAlpha, maxAlpha, 1 - fraction)
        )

        drawIndeterminateBallBeatIndicator(
            maxDiameter = maxBallDiameter.toPx(),
            diameter = diameter,
            ballCount = ballCount,
            spacing = spacing.toPx(),
            color = color,
            alpha = alpha,
        )
    }
}

private fun DrawScope.drawIndeterminateBallBeatIndicator(
    maxDiameter: Float,
    diameter: List<Float>,
    ballCount: Int,
    spacing: Float,
    color: Color,
    alpha: List<Float>
) {
    val offset = maxDiameter + spacing

    for (i in 0 until ballCount) {
        val x = maxDiameter / 2 + offset * i
        drawCircle(
            color = color,
            radius = diameter[i % 2] / 2,
            center = Offset(x, size.height / 2),
            alpha = alpha[i % 2]
        )
    }
}
