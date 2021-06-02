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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.lerp

private const val DefaultAnimationDuration = 1000
private const val DefaultAnimationDelay = 300
private const val DefaultStartDelay = 0

private const val DefaultBallCount = 3
private val DefaultDiameter = 40.dp

@Composable
fun BallScaleMultipleProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    animationDelay: Int = DefaultAnimationDelay,
    startDelay: Int = DefaultStartDelay,
    diameter: Dp = DefaultDiameter,
    ballCount: Int = DefaultBallCount
) {
    val transition = rememberInfiniteTransition()

    val duration = startDelay + animationDuration + animationDelay

    val fraction = mutableListOf<Float>().apply {
        for (i in 0 until ballCount) {
            val delay = startDelay + if (ballCount > 1) animationDelay / (ballCount - 1) * i else 0
            val fraction by transition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = duration
                        0f at delay with LinearOutSlowInEasing
                        1f at animationDuration + delay
                        1f at duration
                    }
                )
            )
            add(fraction)
        }
    }

    ProgressIndicator(modifier, diameter) {
        drawIndeterminateBallScaleMultipleIndicator(
            diameter = fraction.map { lerp(0.dp, diameter, it).toPx() },
            alpha = fraction.map { lerp(1f, 0f, it) },
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallScaleMultipleIndicator(
    diameter: List<Float>,
    alpha: List<Float>,
    color: Color
) {
    for (i in diameter.indices) {
        drawCircle(
            color = color,
            radius = diameter[i] / 2,
            alpha = alpha[i]
        )
    }
}
