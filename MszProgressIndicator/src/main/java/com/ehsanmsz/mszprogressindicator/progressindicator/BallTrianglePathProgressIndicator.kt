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
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction

private const val DefaultAnimationDuration = 650
private const val DefaultStartDelay = 0

private val DefaultWidth = 50.dp
private val DefaultHeight = 40.dp
private val DefaultBallDiameter = 8.dp
private val DefaultStrokeWidth = 1.dp

@Composable
fun BallTrianglePathProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    startDelay: Int = DefaultStartDelay,
    ballDiameter: Dp = DefaultBallDiameter,
    width: Dp = DefaultWidth,
    height: Dp = DefaultHeight,
    strokeWidth: Dp = DefaultStrokeWidth,
) {
    val transition = rememberInfiniteTransition()

    val fraction by transition.fraction(
        durationMillis = animationDuration,
        delayMillis = startDelay,
        easing = FastOutSlowInEasing
    )

    val offset = with(LocalDensity.current) {
        val radius = (ballDiameter.toPx() + strokeWidth.toPx()) / 2
        arrayListOf(
            Offset((width / 2).toPx(), radius),
            Offset(width.toPx() - radius, height.toPx() - radius),
            Offset(radius, height.toPx() - radius),
        )
    }

    val stroke = with(LocalDensity.current) {
        Stroke(strokeWidth.toPx(), cap = StrokeCap.Square)
    }

    ProgressIndicator(modifier, width, height) {
        val offsetList = offset.mapIndexed { index, _ ->
            lerp(offset[index], offset[(index + 1) % 3], fraction)
        }
        drawIndeterminateBallTrianglePathIndicator(
            diameter = ballDiameter.toPx(),
            offset = offsetList,
            color = color,
            stroke = stroke
        )
    }
}

private fun DrawScope.drawIndeterminateBallTrianglePathIndicator(
    diameter: Float,
    offset: List<Offset>,
    color: Color,
    stroke: Stroke
) {
    for (i in offset.indices) {
        drawCircle(
            color = color,
            radius = diameter / 2,
            center = offset[i],
            style = stroke
        )
    }
}
