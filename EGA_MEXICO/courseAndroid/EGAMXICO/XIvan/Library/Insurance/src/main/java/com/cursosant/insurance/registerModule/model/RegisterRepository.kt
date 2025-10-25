package com.cursosant.insurance.registerModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.Constants
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
        callback: (RegisterResult) -> Unit
    ) = withContext(Dispatchers.IO) {
        executeAction(InsuranceException(TypeError.REGISTER)) {
            val result = dataSource.register(firstName, lastName, email, password)
            when {
                result.success -> callback(RegisterResult.Success(result))
                result.status == Constants.STATUS_BAD_REQUEST && result.user != null -> {
                    if (result.user.isActive == true) {
                        callback(RegisterResult.AlreadyRegisteredActive)
                    } else {
                        runCatching { dataSource.resendActivation(email) }
                        callback(RegisterResult.AlreadyRegisteredInactive)
                    }
                }
                else -> throw InsuranceException(TypeError.REGISTER)
            }
        }
    }
}
