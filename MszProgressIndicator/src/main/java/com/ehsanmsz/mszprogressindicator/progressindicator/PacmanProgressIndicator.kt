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
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction
import com.ehsanmsz.mszprogressindicator.fraction2StepReversed
import com.ehsanmsz.mszprogressindicator.lerp

private const val DefaultAnimationDuration = 500

private val DefaultBallDiameter = 8.dp
private val DefaultBallSpacing = 20.dp
private val DefaultWidth = 60.dp
private val DefaultHeight = 30.dp

@Composable
fun PacmanProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration
) {
    val transition = rememberInfiniteTransition()

    val fraction by transition.fraction2StepReversed(
        durationMillis = animationDuration,
        easing = FastOutSlowInEasing
    )
    val translation by transition.fraction(animationDuration)

    ProgressIndicator(modifier, DefaultWidth, DefaultHeight) {
        drawIndeterminatePacmanIndicator(
            diameter = DefaultHeight.toPx(),
            startAngle = lerp(0f, 45f, fraction),
            sweepAngle = lerp(360f, 270f, fraction),
            color = color
        )

        drawIndeterminateBallIndicator(
            diameter = DefaultBallDiameter.toPx(),
            translation = lerp(0.dp, DefaultBallSpacing, translation).toPx(),
            spacing = DefaultBallSpacing.toPx(),
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminatePacmanIndicator(
    diameter: Float,
    startAngle: Float,
    sweepAngle: Float,
    color: Color
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        size = Size(diameter, diameter),
        useCenter = true
    )
}

private fun DrawScope.drawIndeterminateBallIndicator(
    diameter: Float,
    translation: Float,
    spacing: Float,
    color: Color
) {
    val radius = diameter / 2
    for (i in 0..1) {
        val x = size.width - radius - (spacing * i) - translation
        drawCircle(
            color = color,
            radius = radius,
            center = Offset(x, size.height / 2)
        )
    }
}
