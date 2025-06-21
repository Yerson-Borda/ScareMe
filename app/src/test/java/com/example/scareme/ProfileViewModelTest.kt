package com.example.scareme

import com.example.scareme.presentation.user_details.ProfileViewModel
import com.example.scareme.data.repository.iTindrRepository
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ProfileViewModelTest {

    private fun httpException(code: Int): HttpException {
        val errorResponse = Response.error<Any>(code, "".toResponseBody())
        return HttpException(errorResponse)
    }

    /**
     * Code Flaws: Divergent Change & Conditional Complexity
     * Description: Validates that `handleHttpException` sets correct error messages based on HTTP codes.
     */
    @Test
    fun testHandleHttpException_setsExpectedErrorMessages() = runTest {
        val mockRepository = mockk<iTindrRepository>()
        val viewModel = ProfileViewModel(mockRepository)

        val testCases = mapOf(
            400 to "An error occurred, try later",
            401 to "Please, log in again",
            403 to "Please, log in again",
            404 to "Resource not found",
            500 to "Something went wrong, please check your connection",
            999 to "An unexpected error occurred"
        )

        for ((code, expectedMessage) in testCases) {
            viewModel.clearErrorMessage()
            val exception = httpException(code)

            val method = ProfileViewModel::class.java.getDeclaredMethod("handleHttpException", HttpException::class.java)
            method.isAccessible = true
            method.invoke(viewModel, exception)

            assertEquals("For HTTP $code", expectedMessage, viewModel.errorMessage.value)
        }
    }
}