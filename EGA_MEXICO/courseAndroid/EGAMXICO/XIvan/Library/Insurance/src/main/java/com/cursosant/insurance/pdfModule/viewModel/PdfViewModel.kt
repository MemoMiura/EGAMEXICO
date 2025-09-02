package com.cursosant.insurance.pdfModule.viewModel

import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.pdfModule.model.PdfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.pdfModule.viewModel
 * Created by Alain Nicolás Tello on 07/06/23 at 12:55
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
@HiltViewModel
class PdfViewModel @Inject constructor(private val repository: PdfRepository) : BaseViewModel() {
    fun downloadPdf(fileUrl: String, fileName: String){
        executeAction {
            repository.downloadPdf(fileUrl, fileName)
        }
    }
}