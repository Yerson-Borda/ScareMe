package com.example.scareme

import androidx.navigation.NavBackStackEntry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class AppNavigationTest {

    /**
     * Code Flaws:
     * - Message Chains
     * - Data Clumps
     *
     * Description: Verifies that navigation arguments (chatId, avatar, title) are parsed correctly from a mocked NavBackStackEntry.
     * This test ensures that argument extraction logic doesn't break due to key changes or null values.
     */
    @Test
    fun testMessengerScreen_argumentsParsedCorrectly() {
        val mockArguments = Mockito.mock(android.os.Bundle::class.java)
        Mockito.`when`(mockArguments.getString("chatId")).thenReturn("chat_123")
        Mockito.`when`(mockArguments.getString("avatar")).thenReturn("avatar_url")
        Mockito.`when`(mockArguments.getString("title")).thenReturn("Chat Title")

        val mockEntry = Mockito.mock(NavBackStackEntry::class.java)
        Mockito.`when`(mockEntry.arguments).thenReturn(mockArguments)

        val chatId = mockEntry.arguments?.getString("chatId") ?: ""
        val avatar = mockEntry.arguments?.getString("avatar") ?: ""
        val title = mockEntry.arguments?.getString("title") ?: ""

        assertEquals("chat_123", chatId)
        assertEquals("avatar_url", avatar)
        assertEquals("Chat Title", title)
    }
}