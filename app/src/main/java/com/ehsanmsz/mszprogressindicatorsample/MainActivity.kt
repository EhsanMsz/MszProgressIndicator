package com.ehsanmsz.mszprogressindicatorsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.progressindicator.BallBeatProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotateMultipleProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotateProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotatePulseProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallGridBeatProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallGridPulseProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseRiseProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseSyncProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallRotateProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallScaleMultipleProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallScaleProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallScaleRippleMultipleProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallScaleRippleProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallSpinFadeLoaderProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallTrianglePathProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallZigZagDeflectProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallZigZagProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.CubeTransitionProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.LineScalePartyProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.LineScaleProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.LineScalePulseOutProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.LineScalePulseOutRapidProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.LineSpinFadeLoaderProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.PacmanProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.SemiCircleSpinProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.SquareSpinProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.TriangleSkewSpinProgressIndicator
import com.ehsanmsz.mszprogressindicatorsample.ui.theme.MszProgressIndicatorSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MszProgressIndicatorSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Main(it)
                }
            }
        }
    }
}

@Composable
fun Main(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column {
            //Row 1
            BaseRow {
                BallPulseProgressIndicator()
                BallGridPulseProgressIndicator()
                BallClipRotateProgressIndicator()
                BallClipRotatePulseProgressIndicator()
            }
            //Row 2
            BaseRow {
                SquareSpinProgressIndicator()
                BallClipRotateMultipleProgressIndicator()
                BallPulseRiseProgressIndicator()
                BallRotateProgressIndicator()
            }
            //Row 3
            BaseRow {
                CubeTransitionProgressIndicator()
                BallZigZagProgressIndicator()
                BallZigZagDeflectProgressIndicator()
                BallTrianglePathProgressIndicator()
            }
            //Row 4
            BaseRow {
                BallScaleProgressIndicator()
                LineScaleProgressIndicator()
                LineScalePartyProgressIndicator()
                BallScaleMultipleProgressIndicator()
            }
            //Row 5
            BaseRow {
                BallPulseSyncProgressIndicator()
                BallBeatProgressIndicator()
                LineScalePulseOutProgressIndicator()
                LineScalePulseOutRapidProgressIndicator()
            }
            //Row 6
            BaseRow {
                BallScaleRippleProgressIndicator()
                BallScaleRippleMultipleProgressIndicator()
                BallSpinFadeLoaderProgressIndicator()
                LineSpinFadeLoaderProgressIndicator()
            }
            //Row 7
            BaseRow {
                TriangleSkewSpinProgressIndicator()
                PacmanProgressIndicator()
                BallGridBeatProgressIndicator()
                SemiCircleSpinProgressIndicator()
            }
        }
    }
}

@Composable
fun BaseRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    MszProgressIndicatorSampleTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Main(it)
        }
    }
}
