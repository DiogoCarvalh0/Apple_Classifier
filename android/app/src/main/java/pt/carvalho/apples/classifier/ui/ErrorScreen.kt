package pt.carvalho.apples.classifier.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.carvalho.apples.classifier.R
import pt.carvalho.apples.classifier.ui.button.PrimaryButton
import pt.carvalho.apples.classifier.ui.theme.ClassifierTheme

@Composable
internal fun AskPermissionContent(
    invokePermissionPopup: () -> Unit
) {
    ErrorContent(
        emoji = stringResource(id = R.string.camera_icon),
        description = stringResource(id = R.string.camera_permission_explanation),
        buttonText = stringResource(id = R.string.grant_permission),
        onClick = invokePermissionPopup
    )
}

@Composable
internal fun DeniedPermissionContent(
    openPreferences: () -> Unit
) {
    ErrorContent(
        emoji = stringResource(id = R.string.denied_icon),
        description = stringResource(id = R.string.camera_permission_denied),
        buttonText = stringResource(id = R.string.open_settings),
        onClick = openPreferences
    )
}

@Composable
private fun ErrorContent(
    emoji: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emoji,
            fontSize = 72.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = description,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(20.dp),
            color = MaterialTheme.colors.onSurface
        )

        PrimaryButton(
            title = buttonText,
            onClick = onClick
        )
    }
}

@Composable
@Preview
private fun AskPermissionPreview() {
    ClassifierTheme {
        AskPermissionContent { }
    }
}

@Composable
@Preview
private fun DeniedPermissionPreview() {
    ClassifierTheme {
        DeniedPermissionContent { }
    }
}
