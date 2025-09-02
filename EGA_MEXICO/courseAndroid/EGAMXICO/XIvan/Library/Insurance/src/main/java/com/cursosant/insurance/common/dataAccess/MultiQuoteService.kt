package com.cursosant.insurance.common.dataAccess

import com.cursosant.insurance.common.entities.CP
import com.cursosant.insurance.common.entities.QuoteBasic
import com.cursosant.insurance.common.entities.QuoteResponse
import com.cursosant.insurance.common.entities.ServiceResponse
import com.cursosant.insurance.common.entities.StateResponse
import com.cursosant.insurance.common.entities.SuburbsResponse
import com.cursosant.insurance.common.utils.Constants
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.dataAccess
 * Created by Alain Nicolás Tello on 14/06/23 at 16:58
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
interface MultiQuoteService {
    @GET(Constants.PATH_CAR_TYPE)
    suspend fun getTypes() : Map<Int, String>

    @GET(Constants.PATH_STATES)
    suspend fun getStates(@Header(Constants.H_AUTHORIZATION) token: String) : StateResponse

    @GET("${Constants.PATH_CAR_BRAND}{${Constants.P_TYPE}}")
    suspend fun getBrands(@Path(Constants.P_TYPE) type: String) : List<QuoteBasic>

    @GET("${Constants.PATH_CAR_YEAR}{${Constants.P_TYPE}}/{${Constants.P_BRAND}}")
    suspend fun getYears(@Path(Constants.P_TYPE) type: String, @Path(Constants.P_BRAND) brand: String) : List<QuoteBasic>

    @GET("${Constants.PATH_CAR_MODEL}{${Constants.P_TYPE}}/{${Constants.P_BRAND}}/{${Constants.P_YEAR}}")
    suspend fun getModels(@Path(Constants.P_TYPE) type: String,
                          @Path(Constants.P_BRAND) brand: String,
                          @Path(Constants.P_YEAR) year: String) : Map<String, List<QuoteBasic>>

    @GET("${Constants.PATH_CITY}{${Constants.P_STATE_ID}}")
    suspend fun getCities(@Header(Constants.H_AUTHORIZATION) token: String,
                          @Path(Constants.P_STATE_ID) state: String) : List<QuoteBasic>

    @GET("${Constants.PATH_SUBURB}{${Constants.P_STATE_ID}}/{${Constants.P_CITY}}")
    suspend fun getSuburbs(@Header(Constants.H_AUTHORIZATION) token: String,
                           @Path(Constants.P_STATE_ID) state: String,
                           @Path(Constants.P_CITY) city: String) : List<QuoteBasic>

    @GET("${Constants.PATH_CP}{${Constants.P_STATE_ID}}/{${Constants.P_CITY}}/{${Constants.P_SUBURB}}")
    suspend fun getCP(@Header(Constants.H_AUTHORIZATION) token: String,
                      @Path(Constants.P_STATE_ID) state: String,
                      @Path(Constants.P_CITY) city: String,
                      @Path(Constants.P_SUBURB) suburb: String) : CP

    @GET("${Constants.PATH_SUBURBS_BY_CP}{${Constants.P_CP}}")
    suspend fun getSuburbsByCP(@Header(Constants.H_AUTHORIZATION) token: String,
                               @Path(Constants.P_CP) cp: String) : SuburbsResponse

    @POST(Constants.PATH_QUOTE_SERVICES)
    suspend fun getQuote(@Header(Constants.H_AUTHORIZATION) token: String,
                         @Body body: RequestBody) : QuoteResponse

    @POST("${Constants.PATH_QUOTE_SERVICE_RUN}{${Constants.P_SERVICE}}")
    suspend fun getService(@Header(Constants.H_AUTHORIZATION) token: String,
                           @Path(Constants.P_SERVICE) service: String,
                           @Body body: RequestBody) : ServiceResponse
}