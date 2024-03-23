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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction
import com.ehsanmsz.mszprogressindicator.fraction2StepReversed
import com.ehsanmsz.mszprogressindicator.lerp

private const val DefaultAnimationDuration = 700

private val DefaultMaxDiameter = 40.dp
private val DefaultMinDiameter = 28.dp
private val DefaultStrokeWidth = 2.dp
private const val DefaultClipSweepAngle = 120f

@Composable
fun BallClipRotateProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    maxDiameter: Dp = DefaultMaxDiameter,
    minDiameter: Dp = DefaultMinDiameter,
    strokeWidth: Dp = DefaultStrokeWidth,
    clipSweepAngle: Float = DefaultClipSweepAngle
) {
    val transition = rememberInfiniteTransition()

    //Fractional diameter
    val diameter by transition.fraction2StepReversed(animationDuration)

    //Fractional start angle
    val startAngle by transition.fraction(animationDuration)

    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Square)
    }

    ProgressIndicator(modifier, maxDiameter) {
        drawIndeterminateBallClipRotateIndicator(
            maxDiameter = maxDiameter.toPx(),
            diameter = lerp(minDiameter, maxDiameter, diameter).toPx(),
            startAngle = lerp(0f, 360f, startAngle),
            sweepAngle = 360f - clipSweepAngle,
            stroke = stroke,
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallClipRotateIndicator(
    maxDiameter: Float,
    diameter: Float,
    startAngle: Float,
    sweepAngle: Float,
    stroke: Stroke,
    color: Color
) {
    val offset = (stroke.width + maxDiameter - diameter) / 2
    val arcDimen = diameter - stroke.width

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = Offset(x = offset, y = offset),
        size = Size(width = arcDimen, height = arcDimen),
        style = stroke
    )
}
