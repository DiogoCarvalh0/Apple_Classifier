package pt.carvalho.apples.classifier.ui.button

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
internal fun PrimaryButton(
    title: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text = title)
    }
}
