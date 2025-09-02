package com.cursosant.insurance.loginModule.model

import com.cursosant.insurance.common.entities.BaseResponse
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.loginModule.model
 * Created by Alain Nicolás Tello on 31/05/23 at 11:42
 * All rights reserved 2023.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
class LoginRepository @Inject constructor(private val dataSource: DataSource): BaseRepository(){
    suspend fun login(username: String, password: String, callback: () -> Unit) = withContext(Dispatchers.IO){
        executeAction(InsuranceException(TypeError.LOGIN)) {
            val result = dataSource.login(username, password)
            if (result.user > 0) {
                User.apply {
                    instance = result
                    instance?.passwordUser = password
                    saveUser(result.email, password)
                }
                callback()
            } else throw InsuranceException(TypeError.LOGIN)
        }
    }

    suspend fun forgotPassword(email: String, callback: (BaseResponse) -> Unit) = withContext(Dispatchers.IO) {
        executeAction(InsuranceException(TypeError.FORGOT_PASSWORD)) {
            val result = dataSource.forgotPassword(email)
            callback(result)
        }
    }

    suspend fun resendActivation(email: String, callback: (BaseResponse) -> Unit) = withContext(Dispatchers.IO) {
        executeAction(InsuranceException(TypeError.RESEND_ACTIVATION)){
            val result = dataSource.resendActivation(email)
            callback(result)
        }
    }

    suspend fun getPreferences() = dataSource.fetchInitialPreferences()

    private suspend fun saveUser(email: String, password: String) = withContext(Dispatchers.IO){
        executeAction(InsuranceException(TypeError.LOGIN_SAVE_USER)){
            dataSource.updateUserPreferences(email, password)
        }
    }

    fun setupTopics(username: String) = dataSource.setupTopics(username)
}