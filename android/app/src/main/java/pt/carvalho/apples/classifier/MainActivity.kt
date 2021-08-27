package pt.carvalho.apples.classifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import pt.carvalho.apples.classifier.processing.FrameConvertor
import pt.carvalho.apples.classifier.ui.CameraPreview
import pt.carvalho.apples.classifier.utilities.fullscreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()

        setContent {
            MaterialTheme {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    analyzer = FrameConvertor {
                    }
                )
            }
        }
    }
}
