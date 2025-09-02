package com.cursosant.insurance.quoteResultModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.ServiceResponse
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.quoteModule.model
 * Created by Alain Nicolás Tello on 14/06/23 at 14:05
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
class QuoteResultRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository() {
    suspend fun getCoverages(callback: (List<String>) -> Unit) = withContext(Dispatchers.Main) {
        executeAction(InsuranceException(TypeError.QUOTE_COVERAGES)){
            val result = dataSource.getCoverages()
            callback(result)
        }
    }

    suspend fun getFrequencies(callback: (List<String>) -> Unit) = withContext(Dispatchers.Main) {
        executeAction(InsuranceException(TypeError.QUOTE_FREQUENCIES)){
            val result = dataSource.getFrequencies()
            callback(result)
        }
    }

    suspend fun getService(token: String, gender: String?, birthdate: String?, typeId: Int,
                           brand: String?, year: String?, modelId: String?, model: String?, cp: String?,
                           stateId: Long?, state: String?, city: String?, suburb: String?, email: String,
                           xml: String?, coverage: String?, formDataId: Long?, service: Int): ServiceResponse? =
        try {
            dataSource.getService(token, gender, birthdate, typeId, brand, year, modelId, model,
                cp, stateId, state, city, suburb, email, xml, coverage, formDataId, service)
        } catch (e: Exception) {
            null
            //throw InsuranceException(TypeError.QUOTE)
        }
}