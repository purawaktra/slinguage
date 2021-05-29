package com.bangkit.slinguage.data.source.model

/**
 * Data class that captures user information for logged in users retrieved from Repository
 */
data class User(
    var userId: String? = null,
    var email: String? = null,
    var username : String? = null
)