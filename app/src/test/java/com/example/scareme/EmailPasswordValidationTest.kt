package com.example.scareme

import com.example.scareme.domain.UseCases.EmailState
import com.example.scareme.domain.UseCases.PasswordState
import org.junit.Test
import org.junit.Assert.*

class EmailPasswordValidationTest {

    /**
     * Code Flaw: Primitive Obsession
     * Description: Verifies that EmailState correctly fails validation on an invalid email input.
     */
    @Test
    fun testEmailStateValidation_failsWithInvalidInput() {
        val emailState = EmailState()
        emailState.text = "invalid-email"
        emailState.validate()

        assertFalse(emailState.isValid())
        assertEquals("Email invalid-email is invalid", emailState.error)
    }

    /**
     * Code Flaw: Primitive Obsession
     * Description: Verifies that EmailState passes validation for a properly formatted email.
     */
    @Test
    fun testEmailStateValidation_passesWithValidInput() {
        val emailState = EmailState()
        emailState.text = "user@example.com"
        emailState.validate()

        assertTrue(emailState.isValid())
        assertNull(emailState.error)
    }

    /**
     * Code Flaw: Primitive Obsession
     * Description: Ensures PasswordState flags passwords that are too short as invalid.
     */
    @Test
    fun testPasswordStateValidation_failsWithShortPassword() {
        val passwordState = PasswordState()
        passwordState.text = "short"
        passwordState.validate()

        assertFalse(passwordState.isValid())
        assertEquals("Password is invalid", passwordState.error)
    }

    /**
     * Code Flaw: Primitive Obsession
     * Description: Ensures PasswordState accepts valid passwords without triggering errors.
     */
    @Test
    fun testPasswordStateValidation_passesWithValidPassword() {
        val passwordState = PasswordState()
        passwordState.text = "securePassword123"
        passwordState.validate()

        assertTrue(passwordState.isValid())
        assertNull(passwordState.error)
    }
}