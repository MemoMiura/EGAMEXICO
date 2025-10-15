package com.cursosant.insurance.policiesModule.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.model.BaseRepository
import kotlinx.coroutines.flow.Flow
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
class PoliciesRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository() {
    fun getPolicies(token: String): Flow<PagingData<Policy>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PoliciesPagingSource(dataSource, token, PAGE_SIZE) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}