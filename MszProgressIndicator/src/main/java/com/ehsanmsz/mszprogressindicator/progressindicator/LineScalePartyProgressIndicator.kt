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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction2StepReversed

private const val DefaultAnimationDuration = 800
private const val DefaultAnimationDelay = 400
private const val DefaultLineCount = 5

private val DefaultMaxLineHeight = 32.dp
private val DefaultMinLineHeight = 16.dp
private val DefaultMinLineWidth = 2.dp
private val DefaultMaxLineWidth = 3.dp

private val DefaultLineSpacing = 4.dp
private val DefaultLineCornerRadius = 3.dp

@Composable
fun LineScalePartyProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    animationDelay: Int = DefaultAnimationDelay,
    lineCount: Int = DefaultLineCount,
    maxLineHeight: Dp = DefaultMaxLineHeight,
    minLineHeight: Dp = DefaultMinLineHeight,
    minLineWidth: Dp = DefaultMinLineWidth,
    maxLineWidth: Dp = DefaultMaxLineWidth,
    lineSpacing: Dp = DefaultLineSpacing,
    lineCornerRadius: Dp = DefaultLineCornerRadius,
) {
    val transition = rememberInfiniteTransition()

    //Fractional delay
    val delay = arrayOf(.1f, .4f, .2f, .05f, .25f)

    //Fractional scale
    val scale = mutableListOf<Float>().apply {
        for (i in 0 until lineCount) {
            val scale by transition.fraction2StepReversed(
                durationMillis = animationDuration,
                delayMillis = (delay[i % 5] * animationDelay).toInt(),
            )
            add(scale)
        }
    }

    val width = (maxLineWidth + lineSpacing) * lineCount - lineSpacing

    ProgressIndicator(modifier, width, maxLineHeight) {
        drawIndeterminateLineScalePartyIndicator(
            maxLineWidth = maxLineWidth.toPx(),
            maxLineHeight = maxLineHeight.toPx(),
            width = scale.map { lerp(minLineWidth, maxLineWidth, it).toPx() },
            height = scale.map { lerp(minLineHeight, maxLineHeight, it).toPx() },
            cornerRadius = lineCornerRadius.toPx(),
            spacing = lineSpacing.toPx(),
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateLineScalePartyIndicator(
    maxLineWidth: Float,
    maxLineHeight: Float,
    width: List<Float>,
    height: List<Float>,
    cornerRadius: Float,
    spacing: Float,
    color: Color
) {

    for (i in height.indices) {
        val x = (maxLineWidth + spacing) * i + (maxLineWidth - width[i]) / 2
        val y = (maxLineHeight - height[i]) / 2
        drawRoundRect(
            color = color,
            topLeft = Offset(x, y),
            size = Size(width[i], height[i]),
            cornerRadius = CornerRadius(cornerRadius)
        )
    }
}
