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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction
import com.ehsanmsz.mszprogressindicator.getShiftedFraction
import com.ehsanmsz.mszprogressindicator.lerp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val DefaultAnimationDuration = 1000

private val DefaultRectWidth = 3.dp
private val DefaultRectHeight = 12.dp
private val DefaultRectCornerRadius = 2.dp
private val DefaultDiameter = 40.dp

private const val DefaultMaxAlpha = 1f
private const val DefaultMinAlpha = .4f

@Composable
fun LineSpinFadeLoaderProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    rectWidth: Dp = DefaultRectWidth,
    rectHeight: Dp = DefaultRectHeight,
    rectCornerRadius: Dp = DefaultRectCornerRadius,
    diameter: Dp = DefaultDiameter,
    maxAlpha: Float = DefaultMaxAlpha,
    minAlpha: Float = DefaultMinAlpha,
    isClockwise: Boolean = false
) {
    val transition = rememberInfiniteTransition()

    val fraction by transition.fraction(animationDuration)

    ProgressIndicator(modifier, diameter) {
        val alphaList = mutableListOf<Float>()
        for (i in 0..7) {
            val newFraction =
                getShiftedFraction(if (isClockwise) 1 - fraction else fraction, .1f * i)
            lerp(minAlpha, maxAlpha, newFraction).also { alphaList.add(it) }
        }

        drawIndeterminateLineSpinFadeLoaderIndicator(
            rectWidth = rectWidth.toPx(),
            rectHeight = rectHeight.toPx(),
            rectCornerRadius = rectCornerRadius.toPx(),
            alpha = alphaList,
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateLineSpinFadeLoaderIndicator(
    rectWidth: Float,
    rectHeight: Float,
    rectCornerRadius: Float,
    alpha: List<Float>,
    color: Color
) {

    for (i in 0..7) {
        val angle = PI.toFloat() / 4 * i
        val x = size.width * cos(angle) / 2
        val y = size.height * sin(angle) / 2

        rotate(
            degrees = (45f * i) + 90f,
            pivot = center + Offset(x, y)
        ) {
            drawRoundRect(
                color = color,
                size = Size(rectWidth, rectHeight),
                topLeft = center + Offset(-rectWidth / 2, 0f) + Offset(x, y),
                cornerRadius = CornerRadius(rectCornerRadius),
                alpha = alpha[i]
            )
        }
    }
}
