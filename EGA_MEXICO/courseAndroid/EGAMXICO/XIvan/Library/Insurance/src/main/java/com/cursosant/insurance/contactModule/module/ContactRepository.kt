package com.cursosant.insurance.contactModule.module

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.contactModule.module
 * Created by Alain Nicolás Tello on 10/06/23 at 12:46
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
class ContactRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository() {
    suspend fun sendMessage(token: String, name: String, email: String, message: String,
                            callback: () -> Unit) = withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.CONTACT_MESSAGE)){
                val result = dataSource.sendMessage(token, name, email, message)
                if (result.status == Constants.V_CONTACT_STATUS) callback()
                else throw InsuranceException(TypeError.CONTACT_MESSAGE)
            }
        }
}