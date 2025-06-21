package com.example.scareme

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.scareme.presentation.home.HomeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Code Flaws:
     * - Duplicate Code (ensures all reused buttons and text are correctly rendered)
     * - Shotgun Surgery (protects against regressions from small layout changes)
     * - Magic Numbers (implicitly ensures layout remains stable by observing visible elements)
     *
     * Description:
     * Confirms that the main navigation buttons ("Sign In", "Sign Up") and associated label
     * ("Already have an account?") appear on the HomeScreen. Ensures the layout remains
     * user-friendly and consistent across future UI changes.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testHomeScreen_buttonLayoutConsistentAcrossUses() {
        composeTestRule.setContent {
            HomeScreen(navController = rememberNavController())
        }

        // Assert that all important buttons/text elements are present
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign In").assertIsDisplayed()
        composeTestRule.onNodeWithText("Already have an account?").assertIsDisplayed()
    }
}