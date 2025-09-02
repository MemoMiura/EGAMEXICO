package com.cursosant.insurance.common.dataAccess

import com.cursosant.insurance.common.entities.BaseResponse
import com.cursosant.insurance.common.entities.RegisterResponse
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.entities.UserMultiQuoter
import com.cursosant.insurance.common.utils.Constants
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.dataAccess
 * Created by Alain Nicolás Tello on 27/05/23 at 12:29
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
interface UserService {
    @FormUrlEncoded
    @POST(Constants.PATH_LOGIN)
    suspend fun login(@FieldMap params: Map<String, String>) : User//Any

    @FormUrlEncoded
    @POST(Constants.PATH_MULTI_QUOTER)
    suspend fun loginMultiQuoter(@FieldMap params: Map<String, String>) : UserMultiQuoter?

    @FormUrlEncoded
    @POST(Constants.PATH_FORGOT_PASSWORD)
    suspend fun forgotPassword(@FieldMap params: Map<String, String>) : BaseResponse

    @FormUrlEncoded
    @POST(Constants.PATH_RESEND_ACTIVATION)
    suspend fun resendActivation(@FieldMap params: Map<String, String>) : BaseResponse

    @FormUrlEncoded
    @POST(Constants.PATH_REGISTER)
    suspend fun register(@FieldMap params: Map<String, String>) : RegisterResponse

    //@Headers("Content-Type: application/json", "Accept: application/json")
    /*@FormUrlEncoded
    @POST(Constants.PATH_DELETE_ACCOUNT)
    suspend fun deleteAccount(@Header(Constants.H_AUTHORIZATION) token: String,
                              @FieldMap params: Map<String, String>) : BaseResponse*/
    @POST(Constants.PATH_DELETE_ACCOUNT)
    suspend fun deleteAccount(@Header(Constants.H_AUTHORIZATION) token: String,
                              @Body body: RequestBody) : BaseResponse
    @FormUrlEncoded
    @POST(Constants.PATH_DISABLE_ACCOUNT)
    suspend fun disableAccount(@FieldMap params: Map<String, String>) : BaseResponse
}