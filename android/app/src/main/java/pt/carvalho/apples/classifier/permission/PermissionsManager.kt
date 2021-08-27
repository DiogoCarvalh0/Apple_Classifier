package pt.carvalho.apples.classifier.permission

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
internal fun PermissionManager(
    permission: String,
    content: @Composable () -> Unit,
    askPermissionContent: @Composable (askPermission: () -> Unit) -> Unit,
    deniedPermissionContent: @Composable () -> Unit
) {
    val state = rememberPermissionState(permission)

    when {
        state.hasPermission -> content()
        state.shouldShowRationale || !state.permissionRequested -> askPermissionContent {
            state.launchPermissionRequest()
        }
        else -> deniedPermissionContent()
    }
}
