package com.cursosant.insurance.common.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.TypeError
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.signoutModule.model
 * Created by Alain Nicolás Tello on 09/06/23 at 13:52
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
open class BaseDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun signout(callback: (Boolean) -> Unit) {
        try {
            dataStore.edit { preferences ->
                preferences[Constants.K_EMAIL] = ""
                preferences[Constants.K_PASSWORD] = ""
            }
            User.instance = null
            callback(true)
        } catch (e: Exception) {
            throw InsuranceException(TypeError.SIGN_OUT)
        }
    }
}