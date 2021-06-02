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
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction2StepReversed
import com.ehsanmsz.mszprogressindicator.lerp

private val DefaultBallDiameter = 14.dp
private val DefaultSpacing = 4.dp

private const val DefaultAnimationDuration = 1000
private const val DefaultMinAlpha = .5f
private const val DefaultMaxAlpha = 1f

@Composable
fun BallGridBeatProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    ballDiameter: Dp = DefaultBallDiameter,
    minAlpha: Float = DefaultMinAlpha,
    maxAlpha: Float = DefaultMaxAlpha,
    spacing: Dp = DefaultSpacing
) {
    val transition = rememberInfiniteTransition()
    val delay = arrayOf(50, 0, 200, 250, 400, 300, 150, 75, 100)

    val alpha = arrayListOf<Float>().apply {
        for (i in 0..8) {
            val alpha by transition.fraction2StepReversed(
                durationMillis = animationDuration,
                delayMillis = delay[i]
            )
            add(alpha)
        }
    }

    val width = (ballDiameter + spacing) * 3 - spacing

    ProgressIndicator(modifier, width) {
        drawIndeterminateBallGridBeatIndicator(
            diameter = ballDiameter.toPx(),
            alpha = alpha.map { lerp(minAlpha, maxAlpha, it) },
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallGridBeatIndicator(
    diameter: Float,
    alpha: List<Float>,
    color: Color
) {
    val radius = diameter / 2

    for (i in 0..8) {
        val x = (size.width - diameter) * (i % 3) / 2f
        val y = (size.height - diameter) * (i / 3) / 2f
        drawCircle(
            color = color,
            radius = radius,
            center = Offset(radius, radius) + Offset(x, y),
            alpha = alpha[i]
        )
    }
}
