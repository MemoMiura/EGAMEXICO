package com.cursosant.insurance.homeModule.model

import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.homeModule.model
 * Created by Alain Nicolás Tello on 03/01/24 at 18:42
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
class HomeRepository @Inject constructor(private val dataStore: HomeDataStore) {
    suspend fun fetchInitialPreferences() = dataStore.fetchInitialPreferences()
}