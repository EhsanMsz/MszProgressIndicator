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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction

private const val DefaultAnimationDuration = 700

private val DefaultCircleDiameter = 40.dp
private const val DefaultSweepAngle = 130f

@Composable
fun SemiCircleSpinProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    circleDiameter: Dp = DefaultCircleDiameter,
    sweepAngle: Float = DefaultSweepAngle
) {
    val transition = rememberInfiniteTransition()

    //Fractional start angle
    val startAngle by transition.fraction(animationDuration)

    ProgressIndicator(modifier, circleDiameter) {
        drawIndeterminateSemiCircleIndicator(
            diameter = circleDiameter.toPx(),
            startAngle = startAngle * 360f,
            sweepAngle = sweepAngle,
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateSemiCircleIndicator(
    diameter: Float,
    startAngle: Float,
    sweepAngle: Float,
    color: Color
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        useCenter = false,
        sweepAngle = sweepAngle,
        size = Size(width = diameter, height = diameter),
    )
}
