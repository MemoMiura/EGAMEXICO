package com.cursosant.insurance.loginModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.cursosant.insurance.R
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.loginModule.model.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : BaseViewModel() {

    private val _loadingMsg = MutableLiveData<String>()
    val loadingMsg: LiveData<String> = _loadingMsg

    val initialSetupEvent = liveData {
        emit(repository.getPreferences())
    }

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    fun login(username: String, password: String, msg: String) {
        _loadingMsg.value = msg
        executeAction {
            repository.login(username, password) {
                _isLogin.postValue(true)
            }
        }
    }

    fun forgotPassword(email: String, msg: String) {
        _loadingMsg.value = msg
        executeAction {
            repository.forgotPassword(email) { result ->
                if (result.success == true) {
                    showWarning(R.string.login_recovery_email_sent_check)
                } else {
                    showMsg(R.string.login_error_email_not_found)
                }
            }
        }
    }

    fun resendActivation(email: String, msg: String) {
        _loadingMsg.value = msg
        executeAction {
            repository.resendActivation(email) { result ->
                if (result.success == true) {
                    showWarning(R.string.login_activation_email_sent_check)
                } else {
                    showMsg(R.string.login_error_email_not_found)
                }
            }
        }
    }

    fun setupTopics(username: String) {
        executeAction { repository.setupTopics(username) }
    }
}
