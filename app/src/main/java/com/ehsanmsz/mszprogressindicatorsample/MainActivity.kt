package com.ehsanmsz.mszprogressindicatorsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MszProgressIndicatorSampleTheme {
        Main()
    }
}