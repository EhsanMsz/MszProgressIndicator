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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction
import com.ehsanmsz.mszprogressindicator.getShiftedFraction
import com.ehsanmsz.mszprogressindicator.mirrorIndex

private const val DefaultAnimationDuration = 1000
private const val DefaultLineCount = 5

private val DefaultLineHeightMax = 32.dp
private val DefaultLineHeightMin = 16.dp
private val DefaultLineWidth = 3.dp
private val DefaultLineSpacing = 4.dp
private val DefaultLineCornerRadius = 3.dp

@Composable
fun LineScalePulseOutProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    lineCount: Int = DefaultLineCount,
    lineHeightMax: Dp = DefaultLineHeightMax,
    lineHeightMin: Dp = DefaultLineHeightMin,
    lineWidth: Dp = DefaultLineWidth,
    lineSpacing: Dp = DefaultLineSpacing,
    lineCornerRadius: Dp = DefaultLineCornerRadius
) {
    val transition = rememberInfiniteTransition()

    //Fractional height
    val fraction by transition.fraction(animationDuration)

    val width = (lineWidth + lineSpacing) * lineCount - lineSpacing

    ProgressIndicator(modifier, width, lineHeightMax) {
        val heightList = mutableListOf<Float>().apply {
            for (i in 0 until lineCount) {
                val newFraction = getShiftedFraction(fraction, .3f * mirrorIndex(i, lineCount))
                val height = lerp(lineHeightMin, lineHeightMax, newFraction)
                add(height.toPx())
            }
        }

        drawIndeterminateLineScaleIndicator(
            maxHeight = lineHeightMax.toPx(),
            height = heightList,
            width = lineWidth.toPx(),
            cornerRadius = lineCornerRadius.toPx(),
            spacing = lineSpacing.toPx(),
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateLineScaleIndicator(
    maxHeight: Float,
    height: MutableList<Float>,
    width: Float,
    cornerRadius: Float,
    spacing: Float,
    color: Color
) {

    for (i in height.indices) {
        val x = (width + spacing) * i
        val y = (maxHeight - height[i]) / 2
        drawRoundRect(
            color = color,
            topLeft = Offset(x, y),
            size = Size(width, height[i]),
            cornerRadius = CornerRadius(cornerRadius)
        )
    }
}
