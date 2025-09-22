package com.cursosant.insurance.registerModule.model

import com.cursosant.insurance.common.entities.RegisterResponse

sealed class RegisterResult {
    data class Success(val response: RegisterResponse) : RegisterResult()
    object AlreadyRegisteredInactive : RegisterResult()
    object AlreadyRegisteredActive : RegisterResult()
}
