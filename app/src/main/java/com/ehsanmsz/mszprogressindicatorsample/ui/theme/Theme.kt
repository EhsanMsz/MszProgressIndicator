package com.ehsanmsz.mszprogressindicatorsample.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    background = BackgroundColor
)

@Composable
fun MszProgressIndicatorSampleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        shapes = Shapes,
        content = content
    )
}
