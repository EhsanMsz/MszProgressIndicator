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

package com.ehsanmsz.mszprogressindicator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

@Composable
internal fun ProgressIndicator(
    modifier: Modifier,
    size: Dp,
    onDraw: DrawScope.() -> Unit
) {
    Canvas(
        modifier = modifier
            .progressSemantics()
            .size(size)
            .focusable(),
        onDraw = onDraw
    )
}

@Composable
internal fun ProgressIndicator(
    modifier: Modifier,
    width: Dp,
    height: Dp,
    onDraw: DrawScope.() -> Unit
) {
    Canvas(
        modifier = modifier
            .progressSemantics()
            .size(width, height)
            .focusable(),
        onDraw = onDraw
    )
}
