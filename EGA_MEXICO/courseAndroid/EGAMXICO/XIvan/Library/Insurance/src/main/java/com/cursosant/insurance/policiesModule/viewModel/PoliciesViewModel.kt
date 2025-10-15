package com.cursosant.insurance.policiesModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.policiesModule.model.PoliciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policiesModule.viewModel
 * Created by Alain Nicolás Tello on 02/06/23 at 9:52
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
class PoliciesViewModel @Inject constructor(private val repository: PoliciesRepository) : BaseViewModel() {
    private val _policies = MutableLiveData<PagingData<Policy>>()
    val policies: LiveData<PagingData<Policy>> = _policies

    private val _isPoliciesEmpty = MutableLiveData(false)
    val isPoliciesEmpty: LiveData<Boolean> = _isPoliciesEmpty

    private var fetchJob: Job? = null

    fun getPolicies(token: String, username: String) {
        fetchJob?.cancel()
        _isPoliciesEmpty.postValue(false)
        fetchJob = viewModelScope.launch {
            repository.getPolicies(token, username)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _policies.postValue(pagingData)
                }
        }
    }

    fun setPoliciesEmpty(isEmpty: Boolean) {
        _isPoliciesEmpty.postValue(isEmpty)
    }

    fun updateLoading(isLoading: Boolean) {
        setProgress(isLoading)
    }

    fun notifyPoliciesError() {
        showMsg(R.string.policies_error)
    }
}
