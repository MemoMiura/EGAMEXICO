package com.cursosant.insurance.quoteModule.model

import com.cursosant.insurance.common.entities.CP
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.QuoteBasic
import com.cursosant.insurance.common.entities.QuoteResponse
import com.cursosant.insurance.common.entities.State
import com.cursosant.insurance.common.entities.SuburbsResponse
import com.cursosant.insurance.common.entities.UserMultiQuoter
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
class QuoteRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository() {
    suspend fun loginMultiQuoter(username: String, password: String, callback: (UserMultiQuoter?) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.LOGIN_MULTI_QUOTER)){
                try {
                    callback(dataSource.loginMultiQuoter(username, password))
                } catch (e: Exception) {
                    callback(null)
                    //throw FortisException(TypeError.LOGIN_MULTI_QUOTER)
                }
            }
    }

    suspend fun getGenders(callback: (List<String>) -> Unit) = withContext(Dispatchers.Main) {
        executeAction(InsuranceException(TypeError.QUOTE_GENDERS)){
            val result = dataSource.getGenders()
            callback(result)
        }
    }

    suspend fun getTypes(callback: (Map<Int, String>) -> Unit) = withContext(Dispatchers.IO) {
        executeAction(InsuranceException(TypeError.QUOTE_TYPES)){
            val result = dataSource.getTypes()
            callback(result)
        }
    }

    suspend fun getStates(token: String, callback: (List<State>) -> Unit) = withContext(Dispatchers.IO) {
        executeAction(InsuranceException(TypeError.QUOTE_STATES)){
            callback(dataSource.getStates(token))
        }
    }

    suspend fun getBrands(type: String?, callback: (List<String>) -> Unit) = withContext(Dispatchers.IO) {
        executeAction(InsuranceException(TypeError.QUOTE_BRANDS)){
            val result = dataSource.getBrands(type)
            callback(result)
        }
    }

    suspend fun getYears(type: String?, brand: String?, callback: (List<String>) -> Unit) = withContext(Dispatchers.IO) {
        executeAction(InsuranceException(TypeError.QUOTE_YEARS)){
            val result = dataSource.getYears(type, brand)
            callback(result)
        }
    }

    suspend fun getModels(type: String?, brand: String?, year: String?, callback: (List<QuoteBasic>) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.QUOTE_MODELS)) {
                callback(dataSource.getModels(type, brand, year))
            }
        }

    suspend fun getCities(token: String, stateId: Long?, callback: (List<String>) -> Unit) =
        withContext(Dispatchers.IO){
            executeAction(InsuranceException(TypeError.QUOTE_CITIES)) {
                callback(dataSource.getCities(token, stateId))
            }
        }

    suspend fun getSuburbs(token: String, stateId: Long?, city: String?, callback: (List<String>) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.QUOTE_SUBURBS)) {
                callback(dataSource.getSuburbs(token, stateId, city))
            }
        }

    suspend fun getCP(token: String, stateId: Long?, city: String?, suburb: String?,
                      callback: (CP) -> Unit) = withContext(Dispatchers.IO) {
        executeAction(InsuranceException(TypeError.QUOTE_CP)) {
            callback(dataSource.getCP(token, stateId, city, suburb))
        }
    }

    suspend fun getSuburbsByCP(token: String, cp: String?, callback: (SuburbsResponse) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.QUOTE_SUBURBS_BY_CP)){
                callback(dataSource.getSuburbsByCP(token, cp))
            }
        }

    suspend fun getQuote(token: String, gender: String?, birthdate: String?, typeId: Int,
                         brand: String?, year: String?, modelId: String?, model: String?, cp: String?,
                         stateId: Long?, state: String?, city: String?, suburb: String?, email: String,
                         callback: (QuoteResponse) -> Unit) = withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.QUOTE)){
                try {
                callback(dataSource.getQuote(token, gender, birthdate, typeId, brand, year, modelId, model,
                        cp, stateId, state, city, suburb, email))
                } catch (e: Exception) {
                    e.printStackTrace() // FIXME: error if day is big, ej: 24 de julio de 2002
                }
            }
        }
}