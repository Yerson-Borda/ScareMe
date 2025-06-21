package com.example.scareme

import android.content.Context
import com.example.scareme.presentation.sign_up.SignUpViewModel
import com.example.scareme.data.repository.iTindrRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val context = mockk<Context>(relaxed = true)
    private lateinit var viewModel: SignUpViewModel
    private val fakeRepository = mockk<iTindrRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = SignUpViewModel(context)
        val repoField = SignUpViewModel::class.java.getDeclaredField("repository")
        repoField.isAccessible = true
        repoField.set(viewModel, fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Code Flaw: Primitive Obsession & Divergent Change
     * Description: Verifies that with valid inputs, the repository returns a token and no error is set.
     */
    @Test
    fun testRegister_withValidInputs_setsSignUpSuccess() = runTest {
        val expectedToken = "signup-token"
        coEvery { fakeRepository.register("test@example.com", "pass1234") } returns expectedToken

        viewModel.register("test@example.com", "pass1234", "pass1234")

        advanceUntilIdle()

        assertEquals(expectedToken, viewModel.signUpResult.value)
        assertNull(viewModel.errorMessage.value)
    }

    /**
     * Code Flaw: Primitive Obsession & Conditional Complexity
     * Description: Verifies that mismatched passwords are detected before making a network call.
     */
    @Test
    fun testRegister_withMismatchedPasswords_setsError() = runTest {
        viewModel.register("test@example.com", "pass1234", "differentPass")

        assertEquals("Passwords do not match", viewModel.errorMessage.value)
        assertNull(viewModel.signUpResult.value)
    }

    /**
     * Code Flaw: Divergent Change & Conditional Complexity
     * Description: Verifies that HTTP 409 is properly mapped to user-friendly error message.
     */
    @Test
    fun testRegister_whenUserAlreadyExists_setsConflictError() = runTest {
        val fakeRepository = mockk<iTindrRepository>()
        val viewModel = SignUpViewModel(context = mockk(relaxed = true), repository = fakeRepository)

        coEvery {
            fakeRepository.register("test@example.com", "password123")
        } throws HttpException(
            Response.error<Any>(409, "".toResponseBody())
        )

        viewModel.register("test@example.com", "password123", "password123")

        advanceUntilIdle()

        assertEquals("The user already exists", viewModel.errorMessage.value)
        assertNull(viewModel.signUpResult.value)
    }
}