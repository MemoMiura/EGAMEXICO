package com.cursosant.insurance.quoteResultModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.QuoteRequest
import com.cursosant.insurance.common.entities.QuoteResponse
import com.cursosant.insurance.common.entities.ServiceResponse
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.quoteResultModule.model.QuoteResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.quoteResultModule.viewModel
 * Created by Alain Nicolás Tello on 14/06/23 at 11:55
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
class QuoteResultViewModel @Inject constructor(private val repository: QuoteResultRepository) : BaseViewModel() {
    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    val birthdate = MutableLiveData<String>()

    private val _quoteResponse = MutableLiveData<QuoteResponse?>()
    private val quoteResponse: LiveData<QuoteResponse?> = _quoteResponse
    fun setQuoteResponse(quoteResponse: QuoteResponse) {
        _quoteResponse.postValue(quoteResponse)
    }

    private val _quoteRequest = MutableLiveData<QuoteRequest>()
    val quoteRequest: LiveData<QuoteRequest> = _quoteRequest
    fun setQuoteRequest(quoteRequest: QuoteRequest) {
        _quoteRequest.postValue(quoteRequest)
    }

    //ResultView
    private val _coverages = MutableLiveData<List<String>>()
    val coverages: LiveData<List<String>> = _coverages
    val coverage = MutableLiveData<String>()
    fun setCoverage(selectedCoverage: String?) {
        selectedCoverage?.let { coverage.postValue(it) }
    }

    private val _frequencies = MutableLiveData<List<String>>()
    val frequencies: LiveData<List<String>> = _frequencies
    val frequency = MutableLiveData<String>()

    private val _serviceResponse = MutableLiveData<ServiceResponse?>()
    val serviceResponse: LiveData<ServiceResponse?> = _serviceResponse
    val servicesSize = MutableLiveData<Int>()

    fun getAllCoverages() = executeAction {
        repository.getCoverages { result -> _coverages.postValue(result); getAllFrequencies() }
    }

    private fun getAllFrequencies() = executeAction {
        repository.getFrequencies { result -> _frequencies.postValue(result) }
    }

    fun getServices(token: String, email: String) {
        quoteRequest.value?.let {
            with(it) { getServices(token, gender, birthdate, typeId, brand, year,
                modelId, model, cp, stateId, state, city, suburb, email)
            }
        }
    }

    private fun getServices(token: String, gender: String?, birthdate: String?, typeId: Int,
                            brand: String?, year: String?, modelId: String?, model: String?, cp: String?,
                            stateId: Long?, state: String?, city: String?, suburb: String?, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setProgress(Constants.SHOW)
                quoteResponse.value?.services?.let { services ->
                    (services.indices).map { i ->
                        val service = services[i]
                        async {
                            setProgress(Constants.SHOW)
                            val serviceResponse = repository.getService(token, gender, birthdate,
                                typeId, brand, year, modelId, model, cp, stateId,
                                state, city, suburb, email, coverage.value, coverage.value,
                                quoteResponse.value?.form_data_id, service)
                            processResult(serviceResponse)
                        }
                    }.awaitAll()
                    setProgress(Constants.HIDE)
                }
            } catch (e: InsuranceException) {
                setProgress(Constants.HIDE)
                showMsg(e.typeError.resMsg)
            }
        }
    }

    private fun processResult(service: ServiceResponse?): Boolean {
        if (service != null && service.error == null) {
            _serviceResponse.postValue(service)
            return true
        }
        return false
    }
}