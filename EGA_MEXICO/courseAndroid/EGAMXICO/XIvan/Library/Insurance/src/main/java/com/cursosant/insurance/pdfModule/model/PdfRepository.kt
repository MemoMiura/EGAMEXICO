package com.cursosant.insurance.pdfModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.pdfModule.model
 * Created by Alain Nicolás Tello on 07/06/23 at 12:52
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
class PdfRepository @Inject constructor(private val manager: PdfManager) : BaseRepository() {
    suspend fun downloadPdf(fileUrl: String, fileName: String){
        executeAction(InsuranceException(TypeError.PDF_DOWNLOAD)){
            manager.downloadPdf(fileUrl, fileName)
        }
    }
}