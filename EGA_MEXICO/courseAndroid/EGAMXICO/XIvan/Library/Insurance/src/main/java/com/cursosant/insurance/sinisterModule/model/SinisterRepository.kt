package com.cursosant.insurance.sinisterModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.entities.PolicyDetail
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.sinisterModule.model
 * Created by Alain Nicolás Tello on 13/06/23 at 13:40
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
class SinisterRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository() {
    suspend fun getPoliciesInUser(token: String, username: String, callback: (List<Policy>) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.POLICIES)){
                val result = dataSource.getPoliciesInUser(token, username)
                callback(result)
                /*try {
                    val result = dataSource.getPoliciesInUser(token, username)
                    callback(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                }*/
            }
        }

    suspend fun getPolicy(token: String, idPolicy: Long, callback: (PolicyDetail) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.POLICY)){
                val result = dataSource.getPolicy(token, idPolicy)
                callback(result)
            }
        }

    suspend fun sendReport(token: String, policyId: Long, callback: () -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.SINISTER_REPORT)){
                val result = dataSource.sendReport(token, policyId)
                if (result.status != null) callback()
                else throw InsuranceException(TypeError.SINISTER_SENT_REPORT)
            }
        }
}