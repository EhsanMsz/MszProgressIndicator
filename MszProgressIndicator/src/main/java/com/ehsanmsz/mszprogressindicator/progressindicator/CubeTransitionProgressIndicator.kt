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

import androidx.compose.animation.core.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator
import com.ehsanmsz.mszprogressindicator.fraction2Step
import com.ehsanmsz.mszprogressindicator.lerp

private const val DefaultAnimationDuration = 600
private const val DefaultAnimationDelay = 200

private val DefaultWidth = 50.dp
private val DefaultHeight = 40.dp
private val DefaultMaxCubeWidth = 8.dp
private val DefaultMinCubeWidth = 4.dp

@Composable
fun CubeTransitionProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    animationDelay: Int = DefaultAnimationDelay,
    maxCubeWidth: Dp = DefaultMaxCubeWidth,
    minCubeWidth: Dp = DefaultMinCubeWidth,
    width: Dp = DefaultWidth,
    height: Dp = DefaultHeight
) {
    val transition = rememberInfiniteTransition()

    val duration = animationDuration + animationDelay

    //Fractional rotation
    val rotation by transition.fraction2Step(
        durationMillis = animationDuration,
        delayMillis = animationDelay,
        easing = FastOutSlowInEasing
    )

    val offset = with(LocalDensity.current) {
        arrayListOf(
            Offset.Zero,
            Offset(width.toPx() - maxCubeWidth.toPx(), 0f),
            Offset(width.toPx() - minCubeWidth.toPx(), height.toPx() - minCubeWidth.toPx()),
            Offset(0f, height.toPx() - maxCubeWidth.toPx()),
        )
    }

    val offset1 by transition.animateValue(
        initialValue = offset[0],
        targetValue = offset[2],
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector2D(it.x, it.y) },
            convertFromVector = { Offset(it.v1, it.v2) }
        ),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                offset[0] at animationDelay / 2
                offset[1] at duration / 2
                offset[1] at (duration + animationDelay) / 2
                offset[2] at duration
            }
        )
    )

    val offset2 by transition.animateValue(
        initialValue = offset[2],
        targetValue = offset[0],
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector2D(it.x, it.y) },
            convertFromVector = { Offset(it.v1, it.v2) }
        ),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                offset[2] at animationDelay / 2
                offset[3] at duration / 2
                offset[3] at (duration + animationDelay) / 2
                offset[0] at duration
            }
        )
    )

    val minSize = with(LocalDensity.current) { Size(minCubeWidth.toPx(), minCubeWidth.toPx()) }
    val maxSize = with(LocalDensity.current) { Size(maxCubeWidth.toPx(), maxCubeWidth.toPx()) }

    val size by transition.animateValue(
        initialValue = minSize,
        targetValue = maxSize,
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector2D(it.width, it.height) },
            convertFromVector = { Size(it.v1, it.v2) }
        ),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                minSize at animationDelay / 2
                maxSize at duration / 2
                maxSize at (duration + animationDelay) / 2
                minSize at duration
            }
        )
    )

    ProgressIndicator(modifier, width, height) {
        drawIndeterminateCubeTransitionIndicator(
            offsets = listOf(offset1, offset2),
            rotation = lerp(0f, -180f, rotation),
            size = size,
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateCubeTransitionIndicator(
    offsets: List<Offset>,
    size: Size,
    rotation: Float,
    color: Color
) {

    offsets.forEach { offset ->
        rotate(
            degrees = rotation,
            pivot = offset + Offset(size.width / 2, size.height / 2)
        ) {
            drawRect(
                color = color,
                size = size,
                topLeft = offset
            )
        }
    }
}
