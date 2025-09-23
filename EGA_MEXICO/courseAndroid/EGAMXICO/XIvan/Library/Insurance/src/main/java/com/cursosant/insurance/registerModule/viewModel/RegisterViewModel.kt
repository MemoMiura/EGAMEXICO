package com.cursosant.insurance.registerModule.viewModel

import androidx.annotation.StringRes
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

    private val _dialogConfig = MutableLiveData<RegisterDialogConfig?>()
    val dialogConfig: LiveData<RegisterDialogConfig?> = _dialogConfig

    fun register(first: String, last: String, email: String, pass: String) {
        executeAction {
            repository.register(first, last, email, pass) { result ->
                when (result) {
                    is RegisterResult.Success -> {
                        _registerResult.postValue(result)
                        _dialogConfig.postValue(
                            RegisterDialogConfig(
                                titleRes = R.string.register_success_title,
                                messageRes = R.string.register_user_created,
                                navigateToLogin = true
                            )
                        )
                    }
                    RegisterResult.AlreadyRegisteredInactive -> {
                        _dialogConfig.postValue(
                            RegisterDialogConfig(
                                titleRes = R.string.dialog_warning_title,
                                messageRes = R.string.register_user_exists_inactive,
                                navigateToLogin = false
                            )
                        )
                    }
                    RegisterResult.AlreadyRegisteredActive -> {
                        _dialogConfig.postValue(
                            RegisterDialogConfig(
                                titleRes = R.string.dialog_warning_title,
                                messageRes = R.string.register_user_already_active,
                                navigateToLogin = true
                            )
                        )
                    }
                }

            }
        }
    }

    fun onDialogConsumed() {
        _dialogConfig.value = null
    }

}

data class RegisterDialogConfig(
    @StringRes val titleRes: Int,
    @StringRes val messageRes: Int,
    val navigateToLogin: Boolean
)
