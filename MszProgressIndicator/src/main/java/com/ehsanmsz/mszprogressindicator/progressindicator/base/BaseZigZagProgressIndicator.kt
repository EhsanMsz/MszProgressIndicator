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

package com.ehsanmsz.mszprogressindicator.progressindicator.base

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator

@Composable
internal fun BaseZigZagProgressIndicator(
    modifier: Modifier,
    color: Color,
    animationDuration: Int,
    animationDelay: Int,
    ballDiameter: Dp,
    width: Dp,
    height: Dp,
    isReversed: Boolean
) {
    val transition = rememberInfiniteTransition()

    val offset1 = with(LocalDensity.current) {
        val radius = ballDiameter.toPx() / 2
        arrayListOf(
            Offset((width / 2).toPx(), (height / 2).toPx()),
            Offset(radius, radius),
            Offset(width.toPx() - radius, radius),
        )
    }

    val offset2 = with(LocalDensity.current) {
        val radius = ballDiameter.toPx() / 2
        arrayListOf(
            Offset((width / 2).toPx(), (height / 2).toPx()),
            Offset(width.toPx() - radius, height.toPx() - radius),
            Offset(radius, height.toPx() - radius),
        )
    }

    val offset01 by transition.animateValue(
        initialValue = offset1.first(),
        targetValue = offset1.last(),
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector2D(it.x, it.y) },
            convertFromVector = { Offset(it.v1, it.v2) }
        ),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationDuration
                delayMillis = animationDelay
                offset1[1] at animationDuration / 3
                offset1[2] at animationDuration / 3 * 2
                offset1[0] at animationDuration
            },
            repeatMode = if (isReversed) RepeatMode.Reverse else RepeatMode.Restart
        )
    )

    val offset02 by transition.animateValue(
        initialValue = offset2.first(),
        targetValue = offset2.last(),
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector2D(it.x, it.y) },
            convertFromVector = { Offset(it.v1, it.v2) }
        ),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationDuration
                delayMillis = animationDelay
                offset2[1] at animationDuration / 3
                offset2[2] at animationDuration / 3 * 2
                offset2[0] at animationDuration
            },
            repeatMode = if (isReversed) RepeatMode.Reverse else RepeatMode.Restart
        )
    )

    ProgressIndicator(modifier, width, height) {
        drawIndeterminateZigZagIndicator(
            diameter = ballDiameter.toPx(),
            offset = listOf(offset01, offset02),
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateZigZagIndicator(
    diameter: Float,
    offset: List<Offset>,
    color: Color
) {
    offset.forEach {
        drawCircle(
            color = color,
            radius = diameter / 2,
            center = it
        )
    }
}
