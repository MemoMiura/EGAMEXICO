package com.cursosant.insurance.deleteAccountModule.model

import com.cursosant.insurance.common.entities.BaseResponse
import javax.inject.Inject

class DeleteAccountRepository @Inject constructor(
    private val dataSource: DataSource
) {
    // Opción directa (recomendada): devuelve el resultado
    suspend fun disableAccount(username: String, password: String?): BaseResponse =
        dataSource.disableAccount(username, password)

    // Opción con callback (si tu UI ya está hecha así)
    suspend fun disableAccount(
        username: String,
        password: String?,
        callback: (BaseResponse) -> Unit
    ) {
        val result = dataSource.disableAccount(username, password)
        callback(result)
    }
}
