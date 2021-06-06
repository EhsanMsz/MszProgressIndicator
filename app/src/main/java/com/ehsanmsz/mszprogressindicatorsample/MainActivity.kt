package com.ehsanmsz.mszprogressindicatorsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.progressindicator.*
import com.ehsanmsz.mszprogressindicatorsample.ui.theme.MszProgressIndicatorSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MszProgressIndicatorSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    Column(
        modifier = Modifier.fillMaxSize(),
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
fun DefaultPreview() {
    MszProgressIndicatorSampleTheme {
        Main()
    }
}
