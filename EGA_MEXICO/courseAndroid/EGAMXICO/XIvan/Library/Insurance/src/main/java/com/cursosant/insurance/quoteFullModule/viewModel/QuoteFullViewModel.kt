package com.cursosant.insurance.quoteFullModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cursosant.insurance.common.entities.CP
import com.cursosant.insurance.common.entities.QuoteBasic
import com.cursosant.insurance.common.entities.QuoteResponse
import com.cursosant.insurance.common.entities.State
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.quoteFullModule.model.QuoteFullRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteFullViewModel @Inject constructor(
    private val repository: QuoteFullRepository
) : BaseViewModel() {

    // === Campos de formulario (two-way) ===
    private val _token = MutableLiveData<String?>(null)
    val token: MutableLiveData<String?> get() = _token

    private val _gender = MutableLiveData("")
    val gender: MutableLiveData<String> get() = _gender

    private val _frequency = MutableLiveData("")
    val frequency: MutableLiveData<String> get() = _frequency

    private val _type = MutableLiveData("")
    val type: MutableLiveData<String> get() = _type

    private val _brand = MutableLiveData("")
    val brand: MutableLiveData<String> get() = _brand

    private val _year = MutableLiveData("")
    val year: MutableLiveData<String> get() = _year

    private val _model = MutableLiveData("")
    val model: MutableLiveData<String> get() = _model

    private val _cpText = MutableLiveData("")
    val cp: MutableLiveData<String> get() = _cpText

    private val _stateText = MutableLiveData("")
    val state: MutableLiveData<String> get() = _stateText

    private val _cityText = MutableLiveData("")
    val city: MutableLiveData<String> get() = _cityText

    private val _suburbText = MutableLiveData("")
    val suburb: MutableLiveData<String> get() = _suburbText

    private val _coverageText = MutableLiveData("")
    val coverage: MutableLiveData<String> get() = _coverageText
    fun setCoverage(value: String) { _coverageText.value = value }

    private val _age = MutableLiveData("")
    val age: MutableLiveData<String> get() = _age

    private val _birthdate = MutableLiveData("")
    val birthdate: MutableLiveData<String> get() = _birthdate

    private val _birthdateUi = MutableLiveData("")
    val birthdateUi: MutableLiveData<String> get() = _birthdateUi

    private val _checkedCP = MutableLiveData(false)
    val checkedCP: MutableLiveData<Boolean> get() = _checkedCP
    fun setCheckedCP(checked: Boolean) { _checkedCP.value = checked }

    private val _servicesSize = MutableLiveData(0)
    val servicesSize: MutableLiveData<Int> get() = _servicesSize
    fun setServicesSize(size: Int) { _servicesSize.value = size }

    // === Listados para UI ===
    private val _genders = MutableLiveData<List<String>>()
    val genders: LiveData<List<String>> get() = _genders

    private val _types = MutableLiveData<Map<Int, String>>()
    val types: LiveData<Map<Int, String>> get() = _types

    private val _brands = MutableLiveData<List<String>>()
    val carBrands: LiveData<List<String>> get() = _brands

    private val _years = MutableLiveData<List<String>>()
    val carYears: LiveData<List<String>> get() = _years

    private val _models = MutableLiveData<List<QuoteBasic>>()
    val carModels: LiveData<List<QuoteBasic>> get() = _models

    private val _states = MutableLiveData<List<State>>()
    val mxStates: LiveData<List<State>> get() = _states

    private val _cities = MutableLiveData<List<String>>()
    val mxCities: LiveData<List<String>> get() = _cities

    private val _suburbs = MutableLiveData<List<String>>()
    val suburbsList: LiveData<List<String>> get() = _suburbs

    private val _coverages = MutableLiveData<List<String>>()
    val coverages: LiveData<List<String>> get() = _coverages

    private val _frequencies = MutableLiveData<List<String>>()
    val frequencies: LiveData<List<String>> get() = _frequencies

    private val _cpInfo = MutableLiveData<CP>()
    val cpInfo: LiveData<CP> get() = _cpInfo

    private val _quote = MutableLiveData<QuoteResponse?>()
    val quote: LiveData<QuoteResponse?> get() = _quote

    // === Cargas ===
    fun getAllGenders() { _genders.value = repository.getGenders() }
    fun getTypes() { viewModelScope.launch { _types.value = repository.getTypes() } }
    fun getBrands() { viewModelScope.launch { _brands.value = repository.getBrands(type.value) } }
    fun getYears() { viewModelScope.launch { _years.value = repository.getYears(type.value, brand.value) } }
    fun getModels() { viewModelScope.launch { _models.value = repository.getModels(type.value, brand.value, year.value) } }
    fun getStates(token: String) { viewModelScope.launch { _states.value = repository.getStates(token) } }
    fun getCities(token: String) {
        viewModelScope.launch {
            val stateId = _states.value?.firstOrNull { it.name == state.value }?.value
            _cities.value = repository.getCities(token, stateId)
        }
    }
    fun getSuburbs(token: String) {
        viewModelScope.launch {
            val stateId = _states.value?.firstOrNull { it.name == state.value }?.value
            _suburbs.value = repository.getSuburbs(token, stateId, city.value)
        }
    }
    fun getSuburbsByCP(token: String) {
        viewModelScope.launch {
            _suburbs.value = repository.getSuburbsByCP(token, cp.value).colonias?.map { it.name } ?: emptyList()
        }
    }
    fun getCP(token: String) {
        viewModelScope.launch {
            val stateId = _states.value?.firstOrNull { it.name == state.value }?.value
            _cpInfo.value = repository.getCP(token, stateId, city.value, suburb.value)
        }
    }
    fun getCoverages() { _coverages.value = repository.getCoverages() }
    fun getFrequencies() { _frequencies.value = repository.getFrequencies() }

    fun setBirthdate(server: String, ui: String) {
        _birthdate.value = server
        _birthdateUi.value = ui
    }

    // === Cotización ===
    fun getQuote(
        token: String,
        gender: String?,
        birthdate: String?,
        typeId: Int,
        brand: String?,
        year: String?,
        modelId: String?,
        model: String?,
        cp: String?,
        stateId: Long?,
        state: String?,
        city: String?,
        suburb: String?,
        email: String
    ) {
        viewModelScope.launch {
            _quote.value = repository.getQuote(
                token, gender, birthdate, typeId, brand, year,
                modelId, model, cp, stateId, state, city, suburb, email
            )
        }
    }

    fun loginMultiQuoter(username: String, password: String) = viewModelScope.launch {
        _token.value = repository.loginMultiQuoter(username, password)
    }

    fun pause() {
        _quote.value = null
    }
}
