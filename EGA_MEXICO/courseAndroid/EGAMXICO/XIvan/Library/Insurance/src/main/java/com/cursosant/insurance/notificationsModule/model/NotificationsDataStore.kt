package com.cursosant.insurance.notificationsModule.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.cursosant.insurance.common.utils.Constants
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.homeModule.model
 * Created by Alain Nicolás Tello on 03/01/24 at 18:29
 * All rights reserved 2024.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
class NotificationsDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun updatePreferences(isThereNotify: Boolean) {
        dataStore.edit { preferences ->
            preferences[Constants.K_THERE_IS_NOTIFY] = isThereNotify
        }
    }
}