package com.cursosant.insurance.loginModule.viewModel

import androidx.annotation.StringRes
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

    private val _dialogConfig = MutableLiveData<LoginDialogConfig?>()
    val dialogConfig: LiveData<LoginDialogConfig?> = _dialogConfig

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
                val apiError = result.error?.takeIf { it.isNotBlank() }
                val config = when {
                    result.status.equals("resent", ignoreCase = true) -> {
                        LoginDialogConfig(
                            titleRes = R.string.dialog_sent,
                            messageRes = R.string.login_activation_email_sent_check
                        )
                    }
                    apiError?.contains("ya está activo", ignoreCase = true) == true ||
                    apiError?.contains("ya esta activo", ignoreCase = true) == true -> {
                        LoginDialogConfig(
                            titleRes = R.string.dialog_warning_title,
                            messageRes = R.string.register_user_already_active
                        )
                    }
                    apiError?.contains("no existe", ignoreCase = true) == true -> {
                        LoginDialogConfig(
                            titleRes = R.string.dialog_warning_title,
                            messageRes = R.string.login_error_email_not_found
                        )
                    }
                    else -> {
                        LoginDialogConfig(
                            titleRes = R.string.dialog_error_title,
                            messageRes = if (apiError == null) {
                                R.string.login_activation_email_sent_error_unknown
                            } else {
                                null
                            },
                            messageText = apiError
                        )
                    }
                }
                _dialogConfig.postValue(config)
            }
        }
    }

    fun setupTopics(username: String) {
        executeAction { repository.setupTopics(username) }
    }

    fun onDialogConsumed() {
        _dialogConfig.value = null
    }
}

data class LoginDialogConfig(
    @StringRes val titleRes: Int,
    @StringRes val messageRes: Int? = null,
    val messageText: CharSequence? = null
) {
    init {
        require(messageRes != null || !messageText.isNullOrBlank()) {
            "LoginDialogConfig requires a message"
        }
    }
}

