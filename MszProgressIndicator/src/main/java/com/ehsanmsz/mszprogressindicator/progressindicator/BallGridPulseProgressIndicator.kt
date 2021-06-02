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
import androidx.compose.ui.unit.lerp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction2StepReversed

private val DefaultMinBallDiameter = 6.dp
private val DefaultMaxBallDiameter = 14.dp
private val DefaultSpacing = 4.dp

private const val DefaultAnimationDuration = 1000

@Composable
fun BallGridPulseProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    maxBallDiameter: Dp = DefaultMaxBallDiameter,
    minBallDiameter: Dp = DefaultMinBallDiameter,
    spacing: Dp = DefaultSpacing
) {
    val transition = rememberInfiniteTransition()
    val delay = arrayOf(50, 0, 200, 250, 400, 300, 150, 75, 100)

    //Fractional diameter list
    val diameters = arrayListOf<Float>().apply {
        for (i in 0..8) {
            val diameter by transition.fraction2StepReversed(
                durationMillis = animationDuration,
                delayMillis = delay[i]
            )
            add(diameter)
        }
    }

    val width = (maxBallDiameter + spacing) * 3 - spacing

    ProgressIndicator(modifier, width) {
        drawIndeterminateBallGridPulseIndicator(
            maxDiameter = maxBallDiameter.toPx(),
            diameter = diameters.map { lerp(minBallDiameter, maxBallDiameter, it).toPx() },
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallGridPulseIndicator(
    maxDiameter: Float,
    diameter: List<Float>,
    color: Color
) {

    for (i in 0..8) {
        val x = (size.width - maxDiameter) * (i % 3) / 2f
        val y = (size.height - maxDiameter) * (i / 3) / 2f
        drawCircle(
            color = color,
            radius = diameter[i] / 2,
            center = Offset(maxDiameter / 2, maxDiameter / 2) + Offset(x, y)
        )
    }
}
