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

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction2Step
import com.ehsanmsz.mszprogressindicator.fraction2StepReversed
import com.ehsanmsz.mszprogressindicator.lerp

private const val DefaultAnimationDuration = 900
private const val DefaultAnimationDelay = 100

private val DefaultMinBallDiameter = 8.dp
private val DefaultMaxBallDiameter = 16.dp
private val DefaultMinGapSize = 8.dp
private val DefaultMaxGapSize = 12.dp
private val DefaultStrokeWidth = 1.5.dp

@Composable
fun BallClipRotatePulseProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    animationDelay: Int = DefaultAnimationDelay,
    minBallDiameter: Dp = DefaultMinBallDiameter,
    maxBallDiameter: Dp = DefaultMaxBallDiameter,
    minGap: Dp = DefaultMinGapSize,
    maxGap: Dp = DefaultMaxGapSize,
    strokeWidth: Dp = DefaultStrokeWidth
) {
    val transition = rememberInfiniteTransition()

    val fraction by transition.fraction2StepReversed(
        durationMillis = animationDuration,
        delayMillis = animationDelay,
        easing = FastOutSlowInEasing
    )

    //Fractional start angle
    val startAngle by transition.fraction2Step(
        durationMillis = animationDuration,
        delayMillis = animationDelay,
        easing = FastOutSlowInEasing
    )

    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Square)
    }
    val maxDiameter = maxBallDiameter + maxGap + strokeWidth

    ProgressIndicator(modifier, maxDiameter) {
        drawIndeterminateBallClipRotatePulseIndicator(
            diameter = lerp(minBallDiameter, maxBallDiameter, fraction).toPx(),
            gap = lerp(minGap, maxGap, fraction).toPx(),
            startAngle = lerp(0f, 360f, startAngle),
            color = color,
            stroke = stroke
        )
    }
}

private fun DrawScope.drawIndeterminateBallClipRotatePulseIndicator(
    diameter: Float,
    gap: Float,
    startAngle: Float,
    color: Color,
    stroke: Stroke
) {

    drawPath(
        path = getStrokePath(
            startAngle = startAngle,
            radius = (diameter + gap) / 2,
            center = center
        ),
        color = color,
        style = stroke,
    )

    drawCircle(
        color = color,
        radius = diameter / 2,
    )
}

private fun getStrokePath(center: Offset, startAngle: Float, radius: Float): Path {
    return Path().apply {
        for (i in 0..1) {
            addArc(
                startAngleDegrees = startAngle + (180 * i),
                sweepAngleDegrees = 90f,
                oval = Rect(center = center, radius = radius)
            )
        }
    }
}
