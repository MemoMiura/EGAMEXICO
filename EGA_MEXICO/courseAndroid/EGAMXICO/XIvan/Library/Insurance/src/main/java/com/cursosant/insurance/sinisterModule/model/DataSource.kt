package com.cursosant.insurance.sinisterModule.model

import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.entities.PolicyDetail
import com.cursosant.insurance.common.entities.SinisterResponse
import com.cursosant.insurance.common.utils.Constants
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.sinisterModule.model
 * Created by Alain Nicolás Tello on 13/06/23 at 13:36
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
    suspend fun getPoliciesInUser(token: String, username: String): List<Policy>{
        return service.getPoliciesInUser(token, username)
    }

    suspend fun getPolicy(token: String, idPolicy: Long): PolicyDetail {
        val result = service.getPolicyById(token, idPolicy.toString())// as PolicyDetail
        for (i in 0 until result.coverageInPolicy_policy.size){
            result.coverageInPolicy_policy[i].idIndex = i
            result.coverageInPolicy_policy[i].subramo = result.subramo
        }
        return result
    }

    suspend fun sendReport(token: String, policyId: Long): SinisterResponse {
        val params: MutableMap<String, String> = hashMapOf()
        params[Constants.P_POLICY_ID] = policyId.toString()
        return service.sendReport("${Constants.H_BEARER}$token", params)
    }
}