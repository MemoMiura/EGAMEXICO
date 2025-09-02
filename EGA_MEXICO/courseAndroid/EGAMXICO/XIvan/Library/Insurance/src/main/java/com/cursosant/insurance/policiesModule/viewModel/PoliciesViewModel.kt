package com.cursosant.insurance.policiesModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.policiesModule.model.PoliciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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
    private val _policies = MutableLiveData<List<Policy>>()
    val policies: LiveData<List<Policy>> = _policies

    fun getPolicies(token: String) {
        executeAction {
            repository.getPolicies(token){ result ->
                _policies.postValue(result)
            }
        }
    }
}