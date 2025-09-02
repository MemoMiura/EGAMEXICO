package com.cursosant.insurance.quoteResultDetailModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.QuotePayFrequency
import com.cursosant.insurance.common.entities.ServiceResponse
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.quoteResultDetailModule.model
 * Created by Alain Nicolás Tello on 02/08/23 at 20:20
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
class QuoteResultDetailRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository() {
    suspend fun getFrequencies(serviceResponse: ServiceResponse, callback: (List<QuotePayFrequency>) -> Unit) =
        withContext(Dispatchers.Main) {
            executeAction(InsuranceException(TypeError.QUOTE_FREQUENCIES)){
                val result = dataSource.getFrequencies(serviceResponse)
                callback(result)
            }
        }
}