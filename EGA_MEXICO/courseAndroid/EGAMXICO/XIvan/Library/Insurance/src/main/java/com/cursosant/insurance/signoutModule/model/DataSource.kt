package com.cursosant.insurance.signoutModule.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cursosant.insurance.common.model.BaseDataSource
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
class DataSource @Inject constructor(dataStore: DataStore<Preferences>) : BaseDataSource(dataStore) {
    /*suspend fun signout(callback: (Boolean) -> Unit) {
        try {
            dataStore.edit { preferences ->
                preferences[Constants.K_EMAIL] = ""
                preferences[Constants.K_PASSWORD] = ""
            }
            callback(true)
        } catch (e: Exception) {
            throw FortisException(TypeError.SIGN_OUT)
        }
    }*/
}