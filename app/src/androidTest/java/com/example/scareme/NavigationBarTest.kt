package com.example.scareme

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.rememberNavController
import com.example.scareme.presentation.bottomnav.NavigationBar
import org.junit.Rule
import org.junit.Test

class NavigationBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Code Flaws:
     * - Duplicate Code (verifies that each hardcoded NavigationBarItem renders correctly)
     * - Invalid Comments (enforces that icons are exposed via content descriptions)
     *
     * Description:
     * Verifies that the `NavigationBar` component renders icons for all expected destinations:
     * - Cards
     * - Chat
     * - My Profile
     *
     * This helps ensure accessibility and visual consistency across bottom navigation.
     */
    @Test
    fun testNavigationBar_rendersExpectedIcons() {
        composeTestRule.setContent {
            NavigationBar(navController = rememberNavController())
        }

        composeTestRule.onNodeWithContentDescription("Cards").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Chat").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("My Profile").assertIsDisplayed()
    }
}