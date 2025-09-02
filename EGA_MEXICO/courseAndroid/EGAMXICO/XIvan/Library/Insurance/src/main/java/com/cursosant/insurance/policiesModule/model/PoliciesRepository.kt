package com.cursosant.insurance.policiesModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policiesModule.model
 * Created by Alain Nicolás Tello on 02/06/23 at 13:31
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
class PoliciesRepository @Inject constructor(private val dataSource: DataSource): BaseRepository(){
    suspend fun getPolicies(token: String, callback: (List<Policy>) -> Unit) =
        withContext(Dispatchers.IO){
        executeAction(InsuranceException(TypeError.POLICIES)){
            val result = dataSource.getPolicies(token)
            callback(result)
        }
    }
}