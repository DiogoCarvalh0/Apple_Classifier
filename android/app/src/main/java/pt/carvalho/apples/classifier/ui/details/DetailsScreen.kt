package pt.carvalho.apples.classifier.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import pt.carvalho.apples.classifier.model.Apple
import pt.carvalho.apples.classifier.model.SAMPLE
import pt.carvalho.apples.classifier.ui.theme.ClassifierTheme

@Composable
internal fun DetailsScreen(apple: Apple) {
    Column {
        Header(
            title = apple.name,
            description = apple.description,
            picture = apple.picture
        )

        Divider(modifier = Modifier.padding(bottom = 16.dp))

        LazyRow {
            items(apple.related.size) { index ->
                val item = apple.related[index]

                AppleCard(
                    title = item.name,
                    picture = item.picture
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalCoilApi::class)
private fun Header(
    title: String,
    description: String,
    picture: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = picture,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "",
            modifier = Modifier.size(128.dp)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Column {
            Text(
                text = title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = description
            )
        }
    }
}

@Composable
@OptIn(ExperimentalCoilApi::class)
private fun AppleCard(
    title: String,
    picture: String
) {
    Box(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = rememberImagePainter(
                data = picture,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "",
            modifier = Modifier.size(96.dp)
        )
    }
}

@Preview
@Composable
internal fun DetailsScreenPreview() {
    ClassifierTheme {
        DetailsScreen(apple = SAMPLE)
    }
}
