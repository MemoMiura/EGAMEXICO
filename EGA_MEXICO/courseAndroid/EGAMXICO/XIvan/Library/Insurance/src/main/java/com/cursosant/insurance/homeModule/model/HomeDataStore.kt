package com.cursosant.insurance.homeModule.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cursosant.insurance.common.entities.UserPreferences
import com.cursosant.insurance.common.utils.Constants
import kotlinx.coroutines.flow.first
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
class HomeDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun fetchInitialPreferences() = mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): UserPreferences {
        val thereIsNotifications = try {
            preferences[Constants.K_THERE_IS_NOTIFY] ?: false
        } catch (e: Exception) {
            false
        }
        return UserPreferences("", "", thereIsNotifications)
    }
}