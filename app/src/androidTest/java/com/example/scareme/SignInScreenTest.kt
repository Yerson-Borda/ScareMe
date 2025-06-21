package com.example.scareme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.scareme.presentation.sign_in.ISignInViewModel
import com.example.scareme.presentation.sign_in.SignInScreenContent
import com.example.scareme.presentation.sign_in.SignInScreenInternal
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class FakeSignInViewModel : ISignInViewModel {
    override val signInResult = mutableStateOf<String?>(null)
    override val errorMessage = mutableStateOf<String?>(null)

    override fun login(email: String, password: String) {
        signInResult.value = "fake-token"
    }

    override fun resetErrorMessage() {
        errorMessage.value = null
    }
}

@RunWith(AndroidJUnit4::class)
class SignInScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Code Flaws:
     * - God Object
     * - Message Chains
     * - Temporary Field
     *
     * Description: Verifies that `SignInScreenInternal` reacts properly to changes in ViewModel state.
     * - If login is successful, the UI should display a button.
     * - If there's an error, the UI should display the error message.
     */
    @Test
    fun testSignInScreen_reactsToLoginSuccessAndError() {
        val viewModel = FakeSignInViewModel()

        composeTestRule.setContent {
            SignInScreenInternal(
                navController = rememberNavController(),
                viewModel = viewModel,
                navigateToCards = {}
            )
        }

        viewModel.signInResult.value = "fake-token"
        composeTestRule
            .onNode(hasText("Sign In") and hasClickAction())
            .assertIsDisplayed()


        viewModel.signInResult.value = null
        viewModel.errorMessage.value = "Invalid credentials"

        composeTestRule.onNodeWithTag("InlineErrorMessage").assertIsDisplayed()
    }

    /**
     * Code Flaws:
     * - Data Clumps (email/password logic grouped repeatedly)
     * - Duplicate Code (input field handling duplicated across tests)
     *
     * Description: Validates that the Sign In button is:
     * - Disabled when fields are empty or invalid.
     * - Enabled when both email and password are valid.
     * - Triggers `signIn` lambda when clicked with valid inputs.
     */
    @Test
    fun testSignInButton_enabledOnlyWhenFieldsAreValid() {
        var clicked = false

        composeTestRule.setContent {
            SignInScreenContent(
                signIn = { _, _ -> clicked = true },
                errorMessage = null
            )
        }

        composeTestRule
            .onNode(hasText("Sign In") and hasClickAction())
            .assertIsNotEnabled()

        composeTestRule.onNode(hasTestTag("EmailField")).performTextInput("test@example.com")
        composeTestRule.onNode(hasTestTag("PasswordField")).performTextInput("validPassword123")

        composeTestRule
            .onNode(hasText("Sign In") and hasClickAction())
            .assertIsEnabled()

        composeTestRule
            .onNode(hasText("Sign In") and hasClickAction())
            .performClick()


        assert(clicked)
    }
}
