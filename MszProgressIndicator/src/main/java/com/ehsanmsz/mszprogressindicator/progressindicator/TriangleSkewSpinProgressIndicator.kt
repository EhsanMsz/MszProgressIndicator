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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.progressindicator.base.BaseSpinProgressIndicator

private const val DefaultAnimationDuration = 1000
private const val DefaultAnimationDelay = 2000

private val DefaultWidth = 40.dp
private val DefaultHeight = 20.dp

@Composable
fun TriangleSkewSpinProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = DefaultAnimationDuration,
    animationDelay: Int = DefaultAnimationDelay,
    width: Dp = DefaultWidth,
    height: Dp = DefaultHeight
) {
    val path = with(LocalDensity.current) {
        getTrianglePath(Size(width.toPx(), height.toPx()))
    }
    BaseSpinProgressIndicator(
        modifier = modifier,
        color = color,
        animationDuration = animationDuration,
        animationDelay = animationDelay,
        width = width,
        height = height,
        path = path
    )
}

private fun getTrianglePath(size: Size): Path {
    return Path().apply {
        moveTo(size.width / 2, 0f)
        lineTo(0f, size.height)
        lineTo(size.width, size.height)
        close()
    }
}
