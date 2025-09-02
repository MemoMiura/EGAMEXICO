package com.cursosant.insurance.registerModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.RegisterResponse
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val dataSource: DataSource
) : BaseRepository() {

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        callback: (RegisterResponse) -> Unit
    ) = withContext(Dispatchers.IO) {
        executeAction(InsuranceException(TypeError.REGISTER)) {
            val result = dataSource.register(firstName, lastName, email, password)
            if (result.success) {
                callback(result)
            } else {
                throw InsuranceException(TypeError.REGISTER)
            }
        }
    }
}
