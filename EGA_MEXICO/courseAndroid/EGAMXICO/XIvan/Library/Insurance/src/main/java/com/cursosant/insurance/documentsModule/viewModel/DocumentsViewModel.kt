package com.cursosant.insurance.documentsModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.DocumentResponse
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.documentsModule.model.DocumentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.documentsModule.viewModel
 * Created by Alain Nicolás Tello on 07/06/23 at 14:40
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
class DocumentsViewModel @Inject constructor(private val repository: DocumentsRepository) : BaseViewModel() {
    private val _documentResponse = MutableLiveData<DocumentResponse>()
    val documentResponse: LiveData<DocumentResponse> = _documentResponse

    fun getDocuments(token: String){
        executeAction {
            repository.getDocuments(token){ result ->
                _documentResponse.postValue(result)
            }
        }
    }

    fun getDocumentsByInsuranceId(id: Long, subramoCode: Int) {
        executeAction {
            repository.getDocumentsByInsuranceId(id, subramoCode){ result ->
                _documentResponse.postValue(result)
            }
        }
    }
}