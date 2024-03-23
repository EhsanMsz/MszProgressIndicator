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

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DefaultDiameter = 40.dp
private val DefaultStrokeWidth = 1.5.dp
private const val DefaultAnimationDuration = 800
private const val DefaultStartDelay = 300

@Composable
fun BallScaleRippleProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    startDelay: Int = DefaultStartDelay,
    diameter: Dp = DefaultDiameter,
    strokeWidth: Dp = DefaultStrokeWidth
) {
    BallScaleRippleMultipleProgressIndicator(
        modifier = modifier,
        color = color,
        animationDuration = animationDuration,
        animationDelay = 0,
        startDelay = startDelay,
        diameter = diameter,
        strokeWidth = strokeWidth,
        ballCount = 1
    )
}
