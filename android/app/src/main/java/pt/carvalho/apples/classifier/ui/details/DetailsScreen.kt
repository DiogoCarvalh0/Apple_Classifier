package pt.carvalho.apples.classifier.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import pt.carvalho.apples.classifier.R
import pt.carvalho.apples.classifier.model.Apple
import pt.carvalho.apples.classifier.model.SAMPLE
import pt.carvalho.apples.classifier.ui.theme.ClassifierTheme

@Composable
internal fun DetailsScreen(apple: Apple) {
    Header(information = apple)
}

@Composable
@OptIn(ExperimentalCoilApi::class)
private fun Header(information: Apple) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .navigationBarsPadding()
    ) {
        Image(
            painter = rememberImagePainter(
                data = information.picture,
                builder = {
                    crossfade(true)
                    fallback(R.drawable.default_apple)
                }
            ),
            contentDescription = "",
            modifier = Modifier.size(128.dp)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Column {
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = information.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            mapOf(
                stringResource(R.string.country) to information.origin,
                stringResource(R.string.flavour) to information.description,
                stringResource(R.string.confidence) to "${information.confidence}%"
            ).forEach { entry ->
                BulletPoint(
                    modifier = Modifier.padding(bottom = 4.dp),
                    title = entry.key,
                    value = entry.value
                )
            }
        }
    }
}

@Composable
private fun BulletPoint(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    val span = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(title)
        }

        append(": $value")
    }

    Text(
        modifier = modifier.testTag("$title: $value"),
        text = span
    )
}

@Preview
@Composable
internal fun DetailsScreenPreview() {
    ClassifierTheme {
        DetailsScreen(apple = SAMPLE)
    }
}
