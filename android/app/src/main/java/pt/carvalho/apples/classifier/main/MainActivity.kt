package pt.carvalho.apples.classifier.main

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import pt.carvalho.apples.classifier.navigation.Router
import pt.carvalho.apples.classifier.permission.PermissionManager
import pt.carvalho.apples.classifier.processing.converter.FrameConverter
import pt.carvalho.apples.classifier.ui.camera.CameraPreview
import pt.carvalho.apples.classifier.ui.details.DetailsScreen
import pt.carvalho.apples.classifier.ui.error.AskPermissionContent
import pt.carvalho.apples.classifier.ui.error.DeniedPermissionContent
import pt.carvalho.apples.classifier.ui.modal.Bottomsheet
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
                    content = { MainContent(viewModel) },
                    askPermissionContent = { askPermission -> AskPermissionContent(askPermission) },
                    deniedPermissionContent = { DeniedPermissionContent { router.navigateToSystemPreferences() } }
                )
            }
        }
    }

    @Composable
    private fun MainContent(
        viewModel: MainViewModel
    ) {
        val displayState = viewModel.result.value

        if (displayState is MainViewModel.DisplayData.Error) {
            Toast.makeText(this, "No apples found!", Toast.LENGTH_SHORT).show()
        }

        Bottomsheet(
            isExpanded = displayState is MainViewModel.DisplayData.DetectedObject,
            sheetContent = {
                if (displayState is MainViewModel.DisplayData.DetectedObject) {
                    DetailsScreen(apple = displayState.value)
                }
            },
            behindSheetContent = {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    analyzer = FrameConverter(viewModel::process)
                )
            },
            whenCollapsed = viewModel::onDismissed
        )
    }
}
