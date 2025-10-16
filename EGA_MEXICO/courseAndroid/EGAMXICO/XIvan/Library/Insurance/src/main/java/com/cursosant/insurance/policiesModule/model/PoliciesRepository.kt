package com.cursosant.insurance.policiesModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
class PoliciesRepository @Inject constructor(
    private val pagingSource: PoliciesPagingSource
) : BaseRepository() {

    suspend fun getPoliciesPage(
        token: String,
        username: String,
        page: Int
    ): PolicyPagedResponse {
        val normalizedToken = token.trim()
        val normalizedUsername = username.trim()

        if (normalizedToken.isEmpty() || normalizedUsername.isEmpty()) {
            return PolicyPagedResponse.EMPTY
        }

        val exception = InsuranceException(TypeError.POLICIES)

        return withContext(Dispatchers.IO) {
            executeAction(exception) {
                pagingSource.load(
                    token = normalizedToken,
                    username = normalizedUsername,
                    page = page,
                    pageSize = PAGE_SIZE
                )
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
