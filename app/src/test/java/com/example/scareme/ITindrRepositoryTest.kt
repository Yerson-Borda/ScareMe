package com.example.scareme

import com.example.scareme.data.repository.iTindrRepository
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.mockito.Mockito.*
import android.content.Context
import com.example.scareme.common.SaveTokenUtil
import com.example.scareme.data.Network.Auth.AuthApi
import com.example.scareme.data.Network.TokenManager.Token
import com.example.scareme.data.Network.UserData.UserDataApi
import com.example.scareme.domain.Entities.RequestBodies.LoginRequest
import io.mockk.*
import org.junit.Before
import retrofit2.Retrofit

class ITindrRepositoryTest {

    private lateinit var repository: iTindrRepository
    private val context = mock(Context::class.java)

    @Before
    fun setup() {
        repository = spy(iTindrRepository(context))
    }

    /**
     * Code Flaw: Long Method
     * Description: Tests that pagination logic in getUserNamesAndAvatars returns correct subset of users.
     */
    @Test
    fun testGetUserNamesAndAvatarsPagination_returnsCorrectSubset() = runTest {
        val dummyUsers = (1..5).map {
            UserRequest(
                userId = "user$it",
                name = "User $it",
                aboutMyself = "Bio $it",
                avatar = null,
                topics = null
            )
        }

        doReturn(dummyUsers).`when`(repository).getUserList()

        val result = repository.getUserNamesAndAvatars(page = 1, size = 2)

        assertEquals(2, result.size)
        assertEquals("user3", result[0].userId)
        assertEquals("user4", result[1].userId)
    }

    /**
     * Code Flaw: Duplicate Code
     * Description: Validates reusable token retrieval and Retrofit service creation for getMyProfile.
     */
    @Test
    fun testCreateService_withToken_returnsServiceInstance() = runTest {
        val mockRetrofit = mock(Retrofit::class.java)
        val mockService = mock(UserDataApi::class.java)

        mockkObject(SaveTokenUtil)
        every { SaveTokenUtil.getToken(context) } returns "test-token"
        `when`(mockRetrofit.create(UserDataApi::class.java)).thenReturn(mockService)
        `when`(mockService.getMyProfile("Bearer test-token"))!!.thenReturn(
            UserRequest("id", "name", "about", null, null)
        )

        val repository = iTindrRepository(context)
        val retrofitField = iTindrRepository::class.java.getDeclaredField("retrofit")
        retrofitField.isAccessible = true
        retrofitField.set(repository, mockRetrofit)

        val result = repository.getMyProfile()

        verify(mockRetrofit).create(UserDataApi::class.java)
        assertEquals("name", result.name)
    }

    /**
     * Code Flaws: Data Clumps & Duplicate Code
     * Description: Ensures login handles email/password inputs correctly and saves token.
     */
    @Test
    fun testLogin_withEmailAndPassword_returnsToken() = runTest {
        val testEmail = "test@example.com"
        val testPassword = "password123"
        val expectedToken = "mock-token"

        val mockRetrofit = mockk<Retrofit>()
        val mockAuthApi = mockk<AuthApi>()
        val context = mockk<Context>(relaxed = true)

        mockkObject(SaveTokenUtil)
        every { SaveTokenUtil.saveToken(context, expectedToken) } just Runs

        coEvery { mockAuthApi.login(LoginRequest(testEmail, testPassword)) } returns Token(expectedToken)
        every { mockRetrofit.create(AuthApi::class.java) } returns mockAuthApi

        val repository = iTindrRepository(context)
        val retrofitField = iTindrRepository::class.java.getDeclaredField("retrofit")
        retrofitField.isAccessible = true
        retrofitField.set(repository, mockRetrofit)

        val token = repository.login(testEmail, testPassword)

        assertEquals(expectedToken, token)
        verify { SaveTokenUtil.saveToken(context, expectedToken) }
    }

    /**
     * Code Flaw: Duplicate Code
     * Description: Confirms getUserList correctly fetches and returns user data via Retrofit + token.
     */
    @Test
    fun testGetUserList_returnsExpectedUsers() = runTest {
        val token = "test-token"
        val mockRetrofit = mockk<Retrofit>()
        val mockService = mockk<UserDataApi>()
        val context = mockk<Context>(relaxed = true)

        mockkObject(SaveTokenUtil)
        every { SaveTokenUtil.getToken(context) } returns token
        every { mockRetrofit.create(UserDataApi::class.java) } returns mockService

        val expectedUsers = listOf(
            UserRequest("1", "Alice", "About A", null, null),
            UserRequest("2", "Bob", "About B", null, null)
        )
        coEvery { mockService.getUserList("Bearer $token") } returns expectedUsers

        val repository = iTindrRepository(context)
        val retrofitField = iTindrRepository::class.java.getDeclaredField("retrofit")
        retrofitField.isAccessible = true
        retrofitField.set(repository, mockRetrofit)

        val result = repository.getUserList()

        assertEquals(expectedUsers, result)
    }
}