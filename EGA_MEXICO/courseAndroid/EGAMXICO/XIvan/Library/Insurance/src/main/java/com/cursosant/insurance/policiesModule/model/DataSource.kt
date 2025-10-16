package com.cursosant.insurance.policiesModule.model

import com.cursosant.insurance.common.dataAccess.MiuraboxService
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

    suspend fun getPolicies(
        token: String,
        username: String,
        page: Int,
        pageSize: Int
    ): PolicyPagedResponse {
        val normalizedPage = page.takeIf { it > 0 }
        val normalizedPageSize = pageSize.takeIf { it > 0 }
        val normalizedUsername = username.trim()
        val normalizedToken = token.trim()

        if (normalizedUsername.isEmpty() || normalizedToken.isEmpty()) {
            return PolicyPagedResponse.EMPTY
        }

        val authHeader = normalizedToken.takeIf {
            it.startsWith(Constants.H_BEARER, ignoreCase = true)
        } ?: "${Constants.H_BEARER}$normalizedToken"

        val organization = Constants.V_ORGANIZATION.takeUnless { it.isBlank() }

        return service.getPoliciesPaged(
            token = authHeader,
            username = normalizedUsername,
            org = organization,
            page = normalizedPage,
            pageSize = normalizedPageSize
        )
    }
}
