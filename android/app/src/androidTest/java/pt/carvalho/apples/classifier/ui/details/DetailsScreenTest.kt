package pt.carvalho.apples.classifier.ui.details

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import coil.compose.LocalImageLoader
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pt.carvalho.apples.classifier.FakeImageLoader
import pt.carvalho.apples.classifier.R
import pt.carvalho.apples.classifier.ScreenshotComparator
import pt.carvalho.apples.classifier.TestCase
import pt.carvalho.apples.classifier.context
import pt.carvalho.apples.classifier.density
import pt.carvalho.apples.classifier.model.SAMPLE
import pt.carvalho.apples.classifier.string
import pt.carvalho.apples.classifier.ui.theme.ClassifierTheme

internal class DetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ClassifierTheme {
                CompositionLocalProvider(LocalImageLoader provides FakeImageLoader(context())) {
                    DetailsScreen(apple = SAMPLE)
                }
            }
        }
    }

    @Test
    fun details_screen_screenshot_test() {
        ScreenshotComparator.assertScreenshotMatches(
            testCase = testCase,
            node = composeTestRule.onRoot()
        )
    }

    @Test
    fun details_screen_renders_correctly() {
        composeTestRule.onNodeWithText(SAMPLE.name)
            .assertIsDisplayed()

        listOf(
            "${string(R.string.country)}: ${SAMPLE.origin}",
            "${string(R.string.flavour)}: ${SAMPLE.description}",
            "${string(R.string.confidence)}: ${SAMPLE.confidence}%"
        ).forEach { tag ->
            composeTestRule.onNodeWithTag(tag)
                .assertIsDisplayed()
        }
    }

    internal companion object {
        private val testCase = TestCase(
            folder = "details/$density",
            filename = "details"
        )

        @AfterClass
        @JvmStatic
        internal fun cleanup() {
            ScreenshotComparator.clean(testCase)
        }
    }
}