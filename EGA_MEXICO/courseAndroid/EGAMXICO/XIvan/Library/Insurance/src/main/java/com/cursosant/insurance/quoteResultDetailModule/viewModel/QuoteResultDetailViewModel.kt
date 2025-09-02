package com.cursosant.insurance.quoteResultDetailModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.QuotePayFrequency
import com.cursosant.insurance.common.entities.ServiceResponse
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.quoteResultDetailModule.model.QuoteResultDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.quoteResultDetailModule.viewModel
 * Created by Alain Nicolás Tello on 29/07/23 at 10:34
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
class QuoteResultDetailViewModel @Inject constructor(private val repository: QuoteResultDetailRepository) : BaseViewModel() {
    private val _serviceResponse = MutableLiveData<ServiceResponse>()
    val serviceResponse: LiveData<ServiceResponse> = _serviceResponse
    fun setServiceResponse(serviceResponse: ServiceResponse) {
        _serviceResponse.postValue(serviceResponse)
    }

    private val _payFrequencies = MutableLiveData<List<QuotePayFrequency>>()
    val payFrequencies: LiveData<List<QuotePayFrequency>> = _payFrequencies

    fun getPayFrequencies(serviceResponse: ServiceResponse) = executeAction {
        repository.getFrequencies(serviceResponse) { result -> _payFrequencies.postValue(result) }
    }
}