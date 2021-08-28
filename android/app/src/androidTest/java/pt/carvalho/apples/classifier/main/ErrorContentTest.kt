package pt.carvalho.apples.classifier.main

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pt.carvalho.apples.classifier.R
import pt.carvalho.apples.classifier.ScreenshotComparator
import pt.carvalho.apples.classifier.TestCase
import pt.carvalho.apples.classifier.density
import pt.carvalho.apples.classifier.string
import pt.carvalho.apples.classifier.ui.error.AskPermissionContent
import pt.carvalho.apples.classifier.ui.error.DeniedPermissionContent
import pt.carvalho.apples.classifier.ui.theme.ClassifierTheme

internal class ErrorContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val isAskPermissions = mutableStateOf(false)
    private var count = 0

    @Before
    fun setup() {
        composeTestRule.setContent {
            ClassifierTheme {
                if (isAskPermissions.value) {
                    AskPermissionContent {
                        count++
                    }
                } else {
                    DeniedPermissionContent {
                        count++
                    }
                }
            }
        }
    }

    @After
    fun cleanup() {
        count = 0
    }

    @Test
    fun ask_for_permission_screenshot_test() {
        isAskPermissions.value = true

        ScreenshotComparator.assertScreenshotMatches(
            testCase = testCase.copy(filename = "ask_permission"),
            node = composeTestRule.onRoot()
        )
    }

    @Test
    fun ask_for_permission_renders_correctly() {
        isAskPermissions.value = true

        composeTestRule.onNodeWithText(string(R.string.camera_icon))
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(string(R.string.camera_permission_explanation))
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(string(R.string.grant_permission))
            .assertIsDisplayed()
            .performClick()

        assertEquals(1, count)
    }

    @Test
    fun no_permission_granted_screenshot_test() {
        isAskPermissions.value = false

        ScreenshotComparator.assertScreenshotMatches(
            testCase = testCase.copy(filename = "denied_permission"),
            node = composeTestRule.onRoot()
        )
    }

    @Test
    fun no_permission_granted_renders_correctly() {
        isAskPermissions.value = false

        composeTestRule.onNodeWithText(string(R.string.denied_icon))
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(string(R.string.camera_permission_denied))
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(string(R.string.open_settings))
            .assertIsDisplayed()
            .performClick()

        assertEquals(1, count)
    }

    internal companion object {
        private val testCase = TestCase(
            folder = "error/$density",
            // Rendering emojis really triggers a big difference?!
            maxErrorPercentage = 1.5f
        )

        @AfterClass
        @JvmStatic
        internal fun cleanup() {
            ScreenshotComparator.clean(testCase)
        }
    }
}
