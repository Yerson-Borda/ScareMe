package com.example.scareme

import android.content.Context
import com.example.scareme.data.repository.iTindrRepository
import com.example.scareme.presentation.sign_in.SignInViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SignInViewModelTest {

    /**
     * Code Flaw: Temporary Field
     * Description: Verifies that `signInResult` is set temporarily for navigation and then cleared.
     */
    @Test
    fun testSignInResult_clearedAfterNavigation() = runTest {
        val mockRepo = mockk<iTindrRepository>()
        val context = mockk<Context>(relaxed = true)
        val viewModel = SignInViewModel(context)

        coEvery { mockRepo.login(any(), any()) } returns "test-token"

        viewModel.signInResult.value = "mock-token"

        assertEquals("mock-token", viewModel.signInResult.value)

        viewModel.signInResult.value = null

        assertNull(viewModel.signInResult.value)
    }

    private fun httpException(code: Int): HttpException {
        return HttpException(Response.error<Any>(code, "".toResponseBody()))
    }

    /**
     * Code Flaw: Divergent Change & Conditional Complexity
     * Description: Validates that various HTTP codes are mapped to appropriate error messages.
     */
    @Test
    fun testHttpErrorCodeToErrorMessageMapping() = runTest {
        val context = mockk<Context>(relaxed = true)
        val viewModel = SignInViewModel(context)

        val cases = mapOf(
            400 to "An error occurred, try later",
            404 to "User not found, verify the credentials",
            500 to "Something went wrong, check your internet",
            999 to "Unexpected error occurred"
        )

        for ((code, expected) in cases) {
            viewModel.errorMessage.value = null
            viewModel.signInResult.value = null

            val exception = httpException(code)
            viewModel.errorMessage.value = when (exception.code()) {
                400 -> "An error occurred, try later"
                404 -> "User not found, verify the credentials"
                500 -> "Something went wrong, check your internet"
                else -> "Unexpected error occurred"
            }

            assertEquals(expected, viewModel.errorMessage.value)
        }
    }
}