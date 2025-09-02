package com.cursosant.insurance.policyDetailModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.entities.PolicyDetail
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.policyDetailModule.model.PolicyDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policyDetailModule.viewModel
 * Created by Alain Nicolás Tello on 03/06/23 at 12:54
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
class PolicyDetailViewModel @Inject constructor(private val repository: PolicyDetailRepository) : BaseViewModel() {
    private val _policy = MutableLiveData<PolicyDetail>()
    val policy: LiveData<PolicyDetail> = _policy

    private val _isSent = MutableLiveData<Boolean>()
    val isSent: LiveData<Boolean> = _isSent

    fun getPolicy(token: String, idPolicy: Long){
        executeAction {
            repository.getPolicy(token, idPolicy){ result ->
                _policy.postValue(result)
            }
        }
    }

    fun sendReport(token: String) {
        executeAction {
            try {
                repository.sendReport(token, _policy.value?.id) {
                    _isSent.postValue(true)
                }
            } catch (e: InsuranceException) {
                //setToastMsg(e.typeError.resMsg)
            }
        }
    }
}