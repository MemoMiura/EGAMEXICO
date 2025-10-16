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
    /**
     * Expone un [Flow] de [PagingData] para consumir las pólizas de forma paginada.
     *
     * @param token Token de autenticación con prefijo "Bearer" listo para ser enviado en el header.
     */
    fun getPolicies(
        token: String,
        initialPage: Int = PoliciesPagingSource.FIRST_PAGE,
        manualNavigation: Boolean = false,
        onPageMetadata: (PoliciesPageMetadata) -> Unit = {}
    ): Flow<PagingData<Policy>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            initialKey = initialPage,
            pagingSourceFactory = {
                PoliciesPagingSource(
                    dataSource = dataSource,
                    token = token,
                    pageSize = PAGE_SIZE,
                    manualNavigation = manualNavigation,
                    onPageMetadata = onPageMetadata
                )
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}