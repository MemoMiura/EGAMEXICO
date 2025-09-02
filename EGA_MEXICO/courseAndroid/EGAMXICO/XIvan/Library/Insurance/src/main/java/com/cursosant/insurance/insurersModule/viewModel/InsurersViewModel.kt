package com.cursosant.insurance.insurersModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.insurersModule.model.InsurersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.insurersModule.viewModel
 * Created by Alain Nicolás Tello on 02/06/23 at 18:53
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
class InsurersViewModel @Inject constructor(private val repository: InsurersRepository) : BaseViewModel() {
    private val _insurers = MutableLiveData<List<Insurance>>()
    val insurers: LiveData<List<Insurance>> = _insurers

    fun getInsurers(token: String, username: String) {
        executeAction {
            repository.getInsurers(token, username){ result ->
                _insurers.postValue(result)
            }
        }
    }

    fun getInsurersBySubramoId(id: Long) {
        executeAction {
            repository.getInsurersBySubramoId(id){ result ->
                _insurers.postValue(result)
            }
        }
    }
    fun getInsurersBySubramoCode(code: Int) {
        executeAction {
            repository.getInsurersBySubramoCode(code){ result ->
                _insurers.postValue(result)
            }
        }
    }
}