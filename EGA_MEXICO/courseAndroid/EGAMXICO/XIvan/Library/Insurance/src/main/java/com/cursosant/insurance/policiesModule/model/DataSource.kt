package com.cursosant.insurance.policiesModule.model

import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.utils.Constants
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policiesModule.model
 * Created by Alain Nicolás Tello on 02/06/23 at 12:48
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
class DataSource @Inject constructor(private val service: MiuraboxService) {
    suspend fun getPolicies(token: String): List<Policy>{
        return service.getPoliciesByUser("${Constants.H_BEARER}$token")
        /*return try {
            val result = service.getPoliciesByUser("${Constants.H_BEARER}$token")
            result
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }*/
    }
}
