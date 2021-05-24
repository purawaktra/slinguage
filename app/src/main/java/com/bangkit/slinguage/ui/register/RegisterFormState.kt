package com.bangkit.slinguage.ui.register

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val nameError: Int? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val confirPasswordError: Int? = null,
    val isDataValid: Boolean = false
)