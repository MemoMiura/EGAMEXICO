package com.cursosant.insurance.sinisterModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.entities.PolicyDetail
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.sinisterModule.model.SinisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.sinisterModule.viewModel
 * Created by Alain Nicolás Tello on 13/06/23 at 13:43
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
class SinisterViewModel @Inject constructor(private val repository: SinisterRepository) : BaseViewModel() {
    private val _policies = MutableLiveData<List<Policy>>()
    val policies: LiveData<List<Policy>> = _policies

    private val _policy = MutableLiveData<PolicyDetail>()
    val policy: LiveData<PolicyDetail> = _policy

    private val _isSent = MutableLiveData<Boolean>()
    val isSent: LiveData<Boolean> = _isSent

    fun getPoliciesInUser(token: String, username: String){
        executeAction {
            repository.getPoliciesInUser(token, username){ result ->
                _policies.postValue(result)
            }
        }
    }

    fun getPolicy(token: String, idPolicy: Long){
        executeAction {
            repository.getPolicy(token, idPolicy){ result ->
                _policy.postValue(result)
            }
        }
    }

    fun sendReport(token: String, policyId: Long) {
        executeAction {
            try {
                repository.sendReport(token, policyId) {
                    _isSent.postValue(true)
                }
            } catch (e: InsuranceException) {
                //setToastMsg(e.typeError.resMsg)
            }
        }
    }
}