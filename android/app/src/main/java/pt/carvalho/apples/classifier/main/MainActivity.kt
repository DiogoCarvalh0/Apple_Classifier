package pt.carvalho.apples.classifier.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import pt.carvalho.apples.classifier.processing.FrameConvertor
import pt.carvalho.apples.classifier.ui.CameraPreview
import pt.carvalho.apples.classifier.utilities.fullscreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()

        setContent {
            MaterialTheme {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    analyzer = FrameConvertor(viewModel::process)
                )
            }
        }
    }
}
