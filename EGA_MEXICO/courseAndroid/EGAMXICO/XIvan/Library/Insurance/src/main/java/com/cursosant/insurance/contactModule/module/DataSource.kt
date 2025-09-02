package com.cursosant.insurance.contactModule.module

import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.ContactResponse
import com.cursosant.insurance.common.utils.Constants
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.contactModule.module
 * Created by Alain Nicolás Tello on 10/06/23 at 12:39
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
    suspend fun sendMessage(token: String, name: String, email: String, message: String): ContactResponse{
        val params: MutableMap<String, String> = hashMapOf()
        params[Constants.P_CONTACT_NAME] = name
        params[Constants.P_CONTACT_EMAIL] = email
        params[Constants.P_CONTACT_MESSAGE] = message
        return service.sendMessage(token, params)
    }
}