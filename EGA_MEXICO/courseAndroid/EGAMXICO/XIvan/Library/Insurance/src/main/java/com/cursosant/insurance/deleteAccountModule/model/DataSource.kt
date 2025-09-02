package com.cursosant.insurance.deleteAccountModule.model

import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.BaseResponse
import com.cursosant.insurance.common.utils.Constants
import javax.inject.Inject

class DataSource @Inject constructor(
    private val service: MiuraboxService
) {
    suspend fun disableAccount(username: String, password: String?): BaseResponse {
        val params: MutableMap<String, String> = hashMapOf()
        params[Constants.P_USERNAME] = username
        params[Constants.P_PASSWORD] = password ?: ""
        return service.disableAccount(params)
    }
}
