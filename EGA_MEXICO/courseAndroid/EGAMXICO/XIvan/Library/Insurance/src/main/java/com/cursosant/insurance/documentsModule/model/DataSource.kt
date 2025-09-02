package com.cursosant.insurance.documentsModule.model

import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.Document
import com.cursosant.insurance.common.entities.DocumentResponse
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.TemporalData
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.documentsModule.model
 * Created by Alain Nicolás Tello on 07/06/23 at 15:02
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
    suspend fun getDocuments(token: String): DocumentResponse {
        return service.getDocumentsByUser(token, Constants.V_ORGANIZATION)
    }

    fun getDocumentsByInsuranceId(id: Long, subramoCode: Int) : DocumentResponse {
        val result = mutableListOf<Document>()
        TemporalData.getAncoraDocs().forEach {
            if (it.aseguradora.id == id && it.subramo.subramo_code == subramoCode) {
                val document = Document(it.id, it.url, it.org_name, it.arch, it.nombre)
                result.add(document)
            }
        }
        return DocumentResponse(result.size, result)
    }
}