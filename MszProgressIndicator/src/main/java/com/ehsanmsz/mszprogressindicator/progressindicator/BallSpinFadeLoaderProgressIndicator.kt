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
import com.ehsanmsz.mszprogressindicator.fraction
import com.ehsanmsz.mszprogressindicator.getShiftedFraction
import com.ehsanmsz.mszprogressindicator.lerp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val DefaultAnimationDuration = 1000

private val DefaultMaxBallDiameter = 10.dp
private val DefaultMinBallDiameter = 4.dp
private val DefaultDiameter = 40.dp

private const val DefaultMaxAlpha = 1f
private const val DefaultMinAlpha = .4f

@Composable
fun BallSpinFadeLoaderProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    maxBallDiameter: Dp = DefaultMaxBallDiameter,
    minBallDiameter: Dp = DefaultMinBallDiameter,
    diameter: Dp = DefaultDiameter,
    maxAlpha: Float = DefaultMaxAlpha,
    minAlpha: Float = DefaultMinAlpha,
    isClockwise: Boolean = false
) {
    val transition = rememberInfiniteTransition()
    val fraction by transition.fraction(animationDuration)

    ProgressIndicator(modifier, diameter) {
        val diameterList = mutableListOf<Float>()
        val alphaList = mutableListOf<Float>()
        for (i in 0..7) {
            val newFraction =
                getShiftedFraction(if (isClockwise) 1 - fraction else fraction, i * .1f)
            lerp(minBallDiameter, maxBallDiameter, newFraction).also { diameterList.add(it.toPx()) }
            lerp(minAlpha, maxAlpha, newFraction).also { alphaList.add(it) }
        }

        drawIndeterminateBallSpinFadeLoaderIndicator(
            maxDiameter = maxBallDiameter.toPx(),
            alpha = alphaList,
            diameter = diameterList,
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallSpinFadeLoaderIndicator(
    maxDiameter: Float,
    diameter: List<Float>,
    alpha: List<Float>,
    color: Color
) {
    for (i in 0..7) {
        val radius = diameter[i] / 2
        val angle = PI.toFloat() / 4 * i
        val x = (size.width - maxDiameter) * cos(angle) / 2
        val y = (size.height - maxDiameter) * sin(angle) / 2

        drawCircle(
            color = color,
            radius = radius,
            center = center + Offset(x, y),
            alpha = alpha[i]
        )
    }
}
