package pt.carvalho.apples.classifier.ui

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

@Composable
internal fun CameraPreview(
    modifier: Modifier = Modifier,
    analyzer: ImageAnalysis.Analyzer? = null,
    lifecycle: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current
) {
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { viewContext ->
            return@AndroidView PreviewView(viewContext).also { previewView ->
                val executor = ContextCompat.getMainExecutor(viewContext)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        lifecycle,
                        cameraSelector,
                        *listOfNotNull(
                            preview,
                            instantiateAnalyzer(
                                executor = executor,
                                analyzer = analyzer
                            )
                        ).toTypedArray()
                    )
                }, executor)
            }
        },
        modifier = modifier
    )
}

private fun instantiateAnalyzer(
    executor: Executor,
    analyzer: ImageAnalysis.Analyzer?
): ImageAnalysis? {
    if (analyzer == null) return null
    return ImageAnalysis.Builder().build().apply { setAnalyzer(executor, analyzer) }
}
