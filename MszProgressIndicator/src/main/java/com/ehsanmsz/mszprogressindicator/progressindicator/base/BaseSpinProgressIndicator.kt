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

import android.graphics.Camera
import android.graphics.Matrix
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import com.ehsanmsz.mszprogressindicator.ProgressIndicator

@Composable
internal fun BaseSpinProgressIndicator(
    modifier: Modifier,
    color: Color,
    animationDuration: Int,
    animationDelay: Int,
    width: Dp,
    height: Dp,
    path: Path
) {
    val transition = rememberInfiniteTransition()

    val duration = animationDuration + animationDelay

    val xRotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                0f at animationDelay / 4 with LinearEasing
                180f at duration / 4
                180f at duration / 2 + animationDelay / 4 with LinearEasing
                0f at (duration / 4) * 3
                0f at duration
            }
        )
    )

    val yRotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                0f at (duration + animationDelay) / 4 with LinearEasing
                180f at duration / 2
                180f at (duration / 4) * 3 + animationDelay / 4 with LinearEasing
                0f at duration
            }
        )
    )

    val camera = Camera()
    val matrix = Matrix()

    ProgressIndicator(modifier, width, height) {
        drawIndeterminateSpinIndicator(
            color = color,
            xRotation = xRotation,
            yRotation = yRotation,
            path = path,
            matrix = matrix,
            camera = camera,
        )
    }
}

private fun DrawScope.drawIndeterminateSpinIndicator(
    color: Color,
    xRotation: Float,
    yRotation: Float,
    path: Path,
    matrix: Matrix,
    camera: Camera
) {
    matrix.reset()
    camera.save()
    camera.rotateY(yRotation)
    camera.rotateX(xRotation)
    camera.getMatrix(matrix)
    camera.restore()

    matrix.preTranslate(-center.x, -center.y)
    matrix.postTranslate(center.x, center.y)

    drawIntoCanvas { it.nativeCanvas.concat(matrix) }
    drawPath(path = path, color = color)
}
