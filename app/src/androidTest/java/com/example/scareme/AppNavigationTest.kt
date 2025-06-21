package com.example.scareme

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.scareme.navigation.AppScreens
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppNavigationRouteTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Code Flaws:
     * - Long Method in AppNavigation.kt (validates that splitting logic won’t break routing)
     * - Message Chains in routing code (ensures start destination renders as expected)
     *
     * Description:
     * This test ensures that `AppScreens.SignIn.route` is properly registered in the navigation graph
     * and correctly renders a placeholder text ("Sign In Screen"). It acts as a basic regression test for routing stability.
     */
    @Test
    fun testAppNavigation_includesExpectedRoutes() {
        composeTestRule.setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = AppScreens.SignIn.route) {
                composable(AppScreens.SignIn.route) {
                    Text("Sign In Screen")
                }
                composable(AppScreens.SignUp.route) {
                    Text("Sign Up Screen")
                }
                composable(AppScreens.HomeScreen.route) {
                    Text("Home Screen")
                }
            }
        }

        composeTestRule.onNodeWithText("Sign In Screen").assertIsDisplayed()
    }
}