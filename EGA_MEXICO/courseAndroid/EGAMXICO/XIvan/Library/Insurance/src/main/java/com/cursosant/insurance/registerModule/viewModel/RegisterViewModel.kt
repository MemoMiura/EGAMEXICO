package com.cursosant.insurance.registerModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.R
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.registerModule.model.RegisterRepository
import com.cursosant.insurance.registerModule.model.RegisterResult
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

    private val _registerResult = MutableLiveData<RegisterResult.Success>()
    val registerResult: LiveData<RegisterResult.Success> = _registerResult

    private val _showSuccessDialog = MutableLiveData<Boolean>()
    val showSuccessDialog: LiveData<Boolean> = _showSuccessDialog

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> = _navigateToLogin

    fun register(first: String, last: String, email: String, pass: String) {
        executeAction {
            repository.register(first, last, email, pass) { result ->
                when (result) {
                    is RegisterResult.Success -> {
                        _registerResult.postValue(result)
                        showWarning(R.string.register_user_created)
                        _showSuccessDialog.postValue(true)
                    }
                    RegisterResult.AlreadyRegisteredInactive -> {
                        showWarning(R.string.register_user_exists_inactive)
                    }
                    RegisterResult.AlreadyRegisteredActive -> {
                        showMsg(R.string.register_user_already_active)
                        _navigateToLogin.postValue(true)
                    }
                }

            }
        }
    }

    fun onSuccessDialogConsumed() {
        _showSuccessDialog.value = false
    }

    fun onNavigatedToLogin() {
        _navigateToLogin.value = false
    }

}
