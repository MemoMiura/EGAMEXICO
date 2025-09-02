package com.cursosant.insurance.common.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.utils.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.viewModel
 * Created by Alain Nicolás Tello on 31/05/23 at 11:31
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
open class BaseViewModel @Inject constructor() : ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress

    private val _isHideKeyboard = MutableLiveData<Boolean>()
    val isHideKeyboard: LiveData<Boolean> = _isHideKeyboard

    private val _snackbarMsg = MutableLiveData<Int>()
    val snackbarMsg: LiveData<Int> = _snackbarMsg

    private val _snackbarWarning = MutableLiveData<Int>()
    val snackbarWarning: LiveData<Int> = _snackbarWarning

    private val _toastMsg = MutableLiveData<Int>()
    val toastMsg: LiveData<Int> = _toastMsg

    protected fun showMsg(resMsg: Int) {
        _snackbarMsg.postValue(resMsg)
    }

    protected fun showWarning(resMsg: Int) {
        _snackbarWarning.postValue(resMsg)
    }

    protected fun setToastMsg(resMsg: Int) {
        _toastMsg.postValue(resMsg)
    }

    protected fun setProgress(value: Boolean) {
        _inProgress.postValue(value)
    }

    protected fun executeAction(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            _isHideKeyboard.value = Constants.SHOW

            try {
                block()
            } catch (e: InsuranceException){
                _snackbarMsg.value = e.typeError.resMsg
            } finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
}