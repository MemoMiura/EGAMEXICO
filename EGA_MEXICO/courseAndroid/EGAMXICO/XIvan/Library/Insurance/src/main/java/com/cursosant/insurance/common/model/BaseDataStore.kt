package com.cursosant.insurance.common.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.cursosant.insurance.common.utils.Constants
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.model
 * Created by Alain Nicolás Tello on 05/07/23 at 16:09
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
class BaseDataStore @Inject constructor(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = Constants.K_USER_PREFERENCES,
        produceMigrations = { context ->
            listOf(SharedPreferencesMigration(context, Constants.K_USER_PREFERENCES))
        }
    )

    val dataStore: DataStore<Preferences>
        get() =  this.context.dataStore
}