package com.cursosant.insurance.registerModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.RegisterResponse
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.registerModule.model.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.registerModule.viewModel
 * Created by Alain Nicolás Tello on 01/06/23 at 8:19
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
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) : BaseViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    fun register(first: String, last: String, email: String, pass: String) {
        executeAction {
            repository.register(first, last, email, pass) { result ->
                _registerResult.postValue(result)
            }
        }
    }
}
