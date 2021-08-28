package pt.carvalho.apples.classifier.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import pt.carvalho.apples.classifier.R

@Composable
@ReadOnlyComposable
private fun color(id: Int): Color {
    return Color(
        ContextCompat.getColor(LocalContext.current, id)
    )
}

@Composable
internal fun ClassifierTheme(
    content: @Composable () -> Unit
) {
    val colors = MaterialTheme.colors.copy(
        primary = color(id = R.color.red),
        surface = color(id = R.color.black),
        onSurface = color(id = R.color.white)
    )

    MaterialTheme(colors = colors) {
        Surface {
            content()
        }
    }
}
