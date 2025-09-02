package com.cursosant.insurance.loginModule.model

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.cursosant.insurance.common.dataAccess.UserService
import com.cursosant.insurance.common.entities.BaseResponse
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.entities.UserPreferences
import com.cursosant.insurance.common.utils.Constants
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.loginModule.model
 * Created by Alain Nicolás Tello on 31/05/23 at 11:59
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
class DataSource @Inject constructor(private val service: UserService,
                                     private val dataStore: DataStore<Preferences>) {
    suspend fun login(username: String, password: String) : User {//Any {
        val params: MutableMap<String, String> = hashMapOf()
        params[Constants.P_USERNAME] = username
        params[Constants.P_PASSWORD] = password
        params[Constants.P_ORGANIZATION] = Constants.V_ORGANIZATION//"ancora" todo: make customizable?
        return service.login(params)
        /*try {
            return service.login(params)
        } catch (e: Exception){
            e.printStackTrace()
            return User()
        }*/
    }

    suspend fun forgotPassword(email: String) : BaseResponse{
        val params: MutableMap<String, String> = HashMap()
        params[Constants.P_EMAIL] = email
        return service.forgotPassword(params)
    }

    suspend fun resendActivation(email: String) : BaseResponse{
        val params: MutableMap<String, String> = HashMap()
        params[Constants.P_EMAIL] = email
        return service.resendActivation(params)
    }

    suspend fun fetchInitialPreferences() = mapUserPreferences(dataStore.data.first().toPreferences())

    suspend fun updateUserPreferences(email: String, password: String): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences[Constants.K_EMAIL] = email
                preferences[Constants.K_PASSWORD] = password
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences? {
        val email: String?
        val password: String?
        try {
            email = preferences[Constants.K_EMAIL]
            password = preferences[Constants.K_PASSWORD]
        } catch (e: Exception) {
            return null
        }
        return if (!email.isNullOrBlank() && !password.isNullOrBlank())
            UserPreferences(email, password) else null
    }

    fun setupTopics(username: String) {
        if (true) return //fixme: unblock the firebase usage for notifications
        with(FirebaseMessaging.getInstance()) {
            subscribeToTopic(Constants.TOPIC_NEWS)
                .addOnCompleteListener {
                    if (it.isSuccessful) Log.i("CursosANTLog", "Success")
                }
            subscribeToTopic(username)
                .addOnCompleteListener {
                    if (it.isSuccessful) Log.i("CursosANTLog", "Success")
                }
        }
    }
}