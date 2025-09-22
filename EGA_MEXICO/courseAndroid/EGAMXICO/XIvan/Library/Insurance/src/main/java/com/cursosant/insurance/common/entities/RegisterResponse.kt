package com.cursosant.insurance.common.entities

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    val success: Boolean = false,
    val message: String? = null,
    val status: String? = null,
    val error: String? = null,
    val user: RegisterUser? = null
)

data class RegisterUser(
    @SerializedName("last_login")
    val lastLogin: String? = null,
    @SerializedName("is_active")
    val isActive: Boolean? = null,
    val id: Int? = null
)
