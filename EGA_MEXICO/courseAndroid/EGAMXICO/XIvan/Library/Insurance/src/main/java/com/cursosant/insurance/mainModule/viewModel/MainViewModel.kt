package com.cursosant.insurance.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.cursosant.insurance.R
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.loginModule.model.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.mainModule.viewModel
 * Created by Alain Nicolás Tello on 29/11/24 at 11:28
 * All rights reserved 2024.
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
class MainViewModel @Inject constructor() : BaseViewModel(){
    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    fun loginFinished() {
        _isLogin.value = true
    }
}