package com.cursosant.insurance.common.dataAccess

import com.cursosant.insurance.common.entities.ContactResponse
import com.cursosant.insurance.common.entities.DocumentResponse
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.entities.NotificationResponse
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.entities.PolicyDetail
import com.cursosant.insurance.common.entities.SinisterResponse
import com.cursosant.insurance.common.entities.AncoraDocsResponse
import com.cursosant.insurance.common.entities.BaseResponse
import com.cursosant.insurance.common.utils.Constants
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MiuraboxService {

    @GET(Constants.PATH_POLICIES)
    suspend fun getPoliciesByUser(@Header(Constants.H_AUTHORIZATION) token: String) : List<Policy>

    @GET(Constants.PATH_POLICIES + "{${Constants.P_USERNAME}}")
    suspend fun getPoliciesInUser(
        @Header(Constants.H_AUTHORIZATION) token: String,
        @Path(Constants.P_USERNAME) username: String
    ) : List<Policy>

    @GET(Constants.PATH_POLICY + "{${Constants.P_ID}}")
    suspend fun getPolicyById(
        @Header(Constants.H_AUTHORIZATION) token: String,
        @Path(Constants.P_ID) idPolicy: String
    ) : PolicyDetail

    @GET(Constants.PATH_INSURERS + "{${Constants.P_USERNAME}}")
    suspend fun getInsurersByUser(
        @Header(Constants.H_AUTHORIZATION) token: String,
        @Path(Constants.P_USERNAME) username: String
    ) : List<Insurance>

    @GET(Constants.PATH_DOCUMENTS)
    suspend fun getDocumentsByUser(
        @Header(Constants.H_AUTHORIZATION) token: String,
        @Query(Constants.P_ORGANIZATION) org: String
    ) : DocumentResponse

    @FormUrlEncoded
    @POST(Constants.PATH_CONTACT)
    suspend fun sendMessage(
        @Header(Constants.H_AUTHORIZATION) token: String,
        @FieldMap params: Map<String, String>
    ) : ContactResponse

    @FormUrlEncoded
    @POST(Constants.PATH_SINISTER)
    suspend fun sendReport(
        @Header(Constants.H_AUTHORIZATION) token: String,
        @FieldMap params: Map<String, String>
    ) : SinisterResponse

    @GET("${Constants.PATH_NOTIFICATIONS}{${Constants.P_USERNAME}}")
    suspend fun getNotificationsByUser(
        @Header(Constants.H_AUTHORIZATION) token: String,
        @Path(Constants.P_USERNAME) username: String
    ) : NotificationResponse

    @GET("${Constants.PATH_DOC_SUBRAMOS}{${Constants.P_USERNAME}}")
    suspend fun getAncoraDocsByUsername(
        @Header(Constants.H_AUTHORIZATION) token: String,
        @Path(Constants.P_USERNAME) username: String
    ) : List<AncoraDocsResponse>

    @GET("${Constants.PATH_DOC_SUBRAMOS}{${Constants.P_USERNAME}}")
    suspend fun getAncoraDocsByUsernameAny(
        @Header(Constants.H_AUTHORIZATION) token: String,
        @Path(Constants.P_USERNAME) username: String
    ) : Any

    // 🔹 NUEVO: desactivar cuenta por usuario/contraseña (sin header de auth)
    @FormUrlEncoded
    @POST(Constants.PATH_DISABLE_ACCOUNT)
    suspend fun disableAccount(
        @FieldMap body: Map<String, String>
    ): BaseResponse
}
