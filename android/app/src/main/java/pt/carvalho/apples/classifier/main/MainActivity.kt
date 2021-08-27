package pt.carvalho.apples.classifier.main

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import pt.carvalho.apples.classifier.navigation.Router
import pt.carvalho.apples.classifier.permission.PermissionManager
import pt.carvalho.apples.classifier.processing.converter.FrameConverter
import pt.carvalho.apples.classifier.ui.AskPermissionContent
import pt.carvalho.apples.classifier.ui.CameraPreview
import pt.carvalho.apples.classifier.ui.DeniedPermissionContent
import pt.carvalho.apples.classifier.ui.theme.ClassifierTheme
import pt.carvalho.apples.classifier.utilities.fullscreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    @Inject internal lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()

        setContent {
            ClassifierTheme {
                PermissionManager(
                    permission = Manifest.permission.CAMERA,
                    content = {
                        CameraPreview(
                            modifier = Modifier.fillMaxSize(),
                            analyzer = FrameConverter(viewModel::process)
                        )
                    },
                    askPermissionContent = { askPermission -> AskPermissionContent(askPermission) },
                    deniedPermissionContent = { DeniedPermissionContent { router.navigateToSystemPreferences() } }
                )
            }
        }
    }
}
