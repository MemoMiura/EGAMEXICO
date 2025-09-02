package com.cursosant.insurance.documentsModule.model

import com.cursosant.insurance.common.entities.Document
import com.cursosant.insurance.common.entities.DocumentResponse
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.documentsModule.model
 * Created by Alain Nicolás Tello on 07/06/23 at 15:06
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
class DocumentsRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository(){
    suspend fun getDocuments(token: String, callback: (DocumentResponse) -> Unit) =
        withContext(Dispatchers.IO){
        executeAction(InsuranceException(TypeError.DOCUMENTS)){
            val result = dataSource.getDocuments(token)
            callback(result)
        }
    }

    suspend fun getDocumentsByInsuranceId(id: Long, subramoCode: Int, callback: (DocumentResponse) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.DOCUMENTS)){
                callback(dataSource.getDocumentsByInsuranceId(id, subramoCode))
            }
        }
}