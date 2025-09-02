package com.cursosant.insurance.quoteModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.QuoteBasic
import com.cursosant.insurance.common.entities.QuoteRequest
import com.cursosant.insurance.common.entities.QuoteResponse
import com.cursosant.insurance.common.entities.State
import com.cursosant.insurance.common.utils.DateUtils
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.quoteModule.model.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.quoteModel.viewModel
 * Created by Alain Nicolás Tello on 14/06/23 at 12:45
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
class QuoteViewModel @Inject constructor(private val repository: QuoteRepository,
                                         private val utils: DateUtils) : BaseViewModel() {
    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    val birthdate = MutableLiveData<String>()
    fun setBirthdate(dateQuote: String?, dateUI: String) {
        dateQuote?.let {
            birthdate.postValue(it)
            age.postValue( utils.getAge(dateUI))
        }
    }
    private val age = MutableLiveData<Int>()

    private val _genders = MutableLiveData<List<String>>()
    val genders: LiveData<List<String>> = _genders
    val gender = MutableLiveData<String>()

    private val _carTypes = MutableLiveData<Map<Int, String>>()
    val carTypes: LiveData<Map<Int, String>> = _carTypes
    val type = MutableLiveData<String>()

    private val _carBrands = MutableLiveData<List<String>>()
    val carBrands: LiveData<List<String>> = _carBrands
    val brand = MutableLiveData<String>()

    private val _carYears = MutableLiveData<List<String>>()
    val carYears: LiveData<List<String>> = _carYears
    val year = MutableLiveData<String>()

    private val _carModels = MutableLiveData<List<QuoteBasic>>()
    val carModels: LiveData<List<QuoteBasic>> = _carModels
    val model = MutableLiveData<String>()

    private val _mxStates = MutableLiveData<List<State>>()
    val mxStates: LiveData<List<State>> = _mxStates
    val state = MutableLiveData<String>()

    private val _mxCities = MutableLiveData<List<String>>()
    val mxCities: LiveData<List<String>> = _mxCities
    val city = MutableLiveData<String>()

    private val _suburbs = MutableLiveData<List<String>>()
    val suburbs: LiveData<List<String>> = _suburbs
    val suburb = MutableLiveData<String>()

    val cp = MutableLiveData<String>()

    private val _quoteResponse = MutableLiveData<QuoteResponse?>()
    val quoteResponse: LiveData<QuoteResponse?> = _quoteResponse

    val isCheckedCP = MutableLiveData(false)

    //ResultView
    var quoteRequest = QuoteRequest()

    fun pause() = _quoteResponse.postValue(null)

    fun loginMultiQuoter(username: String, password: String) = executeAction {
        repository.loginMultiQuoter(username, password){ result ->
            _token.postValue(result?.token) }
    }

    fun getAllGenders() = executeAction {
        repository.getGenders { result -> _genders.postValue(result) }
    }

    fun getTypes() = executeAction {
        repository.getTypes { result -> _carTypes.postValue(result) }
    }

    fun getBrands() = executeAction {
        repository.getBrands(type.value){ result -> _carBrands.postValue(result) }
    }

    fun getYears() = executeAction {
        repository.getYears(type.value, brand.value){ result -> _carYears.postValue(result) }
    }

    fun getModels() = executeAction {
        repository.getModels(type.value, brand.value, year.value){ result -> _carModels.postValue(result) }
    }

    fun getStates(token: String) = executeAction {
        repository.getStates(token) { result -> _mxStates.postValue(result) }
    }

    fun getCities(token: String) = executeAction {
        repository.getCities(token, stateId()) { result -> _mxCities.postValue(result)}
    }

    fun getSuburbs(token: String) = executeAction {
        repository.getSuburbs(token, stateId(), city.value) { result -> _suburbs.postValue(result)}
    }

    fun getCP(token: String) = executeAction {
        repository.getCP(token, stateId(), city.value, suburb.value) { result -> cp.postValue(result.cp)}
    }

    fun getSuburbsByCP(token: String) {
        if (cp.value?.length == 5) {
            executeAction { repository.getSuburbsByCP(token, cp.value) { result ->
                state.postValue(result.estado?.name ?: "")
                city.postValue(result.municipio ?: "")
                _suburbs.postValue(result.colonias?.map { it.name }) }
            }
        } else {
            suburb.postValue("")
        }
    }

    fun getQuote(token: String, email: String) = executeAction {
        val typeId = carTypes.value?.entries?.find { it.value == type.value }?.key ?: 0
        val modelId = carModels.value?.find { it.name == model.value }?.value
        repository.getQuote(token, gender.value, birthdate.value, typeId, brand.value, year.value, modelId, model.value,
            cp.value, stateId(), state.value, city.value, suburb.value, email){ result ->
            //prepare to QuoteResult
            quoteRequest = QuoteRequest(gender.value, birthdate.value, typeId, brand.value, year.value,
                modelId, model.value, cp.value, stateId(), state.value, city.value, suburb.value, age.value)

            _quoteResponse.postValue(result)
        }
    }

    private fun stateId() = mxStates.value?.find { it.name == state.value }?.value
}