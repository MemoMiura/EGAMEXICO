package com.cursosant.insurance.policyDetailModule.model

import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.dataAccess.UserService
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.entities.PolicyDetail
import com.cursosant.insurance.common.entities.SinisterResponse
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
    suspend fun getPolicy(token: String, idPolicy: Long): PolicyDetail{
        val result = service.getPolicyById(token, idPolicy.toString())
        //result = (result as PolicyDetail)
        for (i in 0 until result.coverageInPolicy_policy.size){
            result.coverageInPolicy_policy[i].idIndex = i
            result.coverageInPolicy_policy[i].subramo = result.subramo
        }
        return result
    }

    suspend fun sendReport(token: String, policyId: Long?): SinisterResponse {
        val params: MutableMap<String, String> = hashMapOf()
        params[Constants.P_POLICY_ID] = policyId.toString()
        return service.sendReport("${Constants.H_BEARER}$token", params)
    }
}