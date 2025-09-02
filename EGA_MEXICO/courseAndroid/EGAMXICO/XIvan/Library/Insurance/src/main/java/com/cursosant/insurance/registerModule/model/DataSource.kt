package com.cursosant.insurance.registerModule.model

import com.cursosant.insurance.common.dataAccess.UserService
import com.cursosant.insurance.common.entities.RegisterResponse
import com.cursosant.insurance.common.utils.Constants
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.registerModule.model
 * Created by Alain Nicolás Tello on 01/06/23 at 13:56
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
class DataSource @Inject constructor(private val service: UserService) {
    suspend fun register(firstName: String, lastName: String, email: String, password: String) : RegisterResponse {
        val params: MutableMap<String, String> = hashMapOf()
        params[Constants.P_FIRST_NAME] = firstName
        params[Constants.P_LAST_NAME] = lastName
        params[Constants.P_EMAIL] = email
        params[Constants.P_PASSWORD] = password
        params[Constants.P_ORGANIZATION] = Constants.V_ORGANIZATION
        return service.register(params)
    }
}