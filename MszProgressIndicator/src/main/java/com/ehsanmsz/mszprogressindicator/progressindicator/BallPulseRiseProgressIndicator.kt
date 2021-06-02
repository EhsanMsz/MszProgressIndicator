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

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
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

private const val DefaultAnimationDuration = 1000

private val DefaultHeight = 30.dp
private val DefaultMinBallDiameter = 6.dp
private val DefaultMaxBallDiameter = 10.dp
private val DefaultSpacing = 10.dp

@Composable
fun BallPulseRiseProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    minBallDiameter: Dp = DefaultMinBallDiameter,
    maxBallDiameter: Dp = DefaultMaxBallDiameter,
    height: Dp = DefaultHeight,
    spacing: Dp = DefaultSpacing
) {
    val transition = rememberInfiniteTransition()

    //Fractional diameter
    val diameter1 by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationDuration
                0f at animationDuration / 4
                1f at animationDuration / 2
                1f at animationDuration
            }
        )
    )

    //Fractional diameter
    val diameter2 by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationDuration
                1f at animationDuration / 2
                0f at animationDuration / 4 * 3
                1f at animationDuration
            }
        )
    )

    //Fractional height
    val h by transition.fraction2StepReversed(animationDuration)

    val maxWidth = (maxBallDiameter + spacing) * 3 - spacing
    val maxHeight = height + maxBallDiameter

    ProgressIndicator(modifier, maxWidth, maxHeight) {
        val diameterList = listOf(
            lerp(minBallDiameter, maxBallDiameter, diameter1).toPx(),
            lerp(minBallDiameter, maxBallDiameter, diameter2).toPx()
        )

        drawIndeterminateBallPulseRiseIndicator(
            maxDiameter = maxBallDiameter.toPx(),
            maxHeight = height.toPx(),
            diameter = diameterList,
            spacing = spacing.toPx(),
            height = lerp(0.dp, height, h).toPx(),
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallPulseRiseIndicator(
    maxDiameter: Float,
    maxHeight: Float,
    diameter: List<Float>,
    spacing: Float,
    height: Float,
    color: Color
) {
    val offset = maxDiameter + spacing

    for (i in 0..2) {
        val x = maxDiameter / 2 + offset * i
        drawCircle(
            color = color,
            radius = diameter[0] / 2,
            center = Offset(x, height + diameter[0] / 2)
        )
    }

    val minWidthOffset = (size.width - offset) / 2

    for (i in 0..1) {
        val x = minWidthOffset + offset * i
        drawCircle(
            color = color,
            radius = diameter[1] / 2,
            center = Offset(x, maxHeight - height + diameter[1] / 2)
        )
    }
}
