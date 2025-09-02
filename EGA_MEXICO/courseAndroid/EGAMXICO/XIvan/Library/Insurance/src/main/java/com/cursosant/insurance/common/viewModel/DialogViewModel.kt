package com.cursosant.insurance.common.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
open class DialogViewModel @Inject constructor() : BaseViewModel() {
    private val _dismiss = MutableLiveData<Boolean>()
    val dismiss: LiveData<Boolean> = _dismiss

    fun setDismiss(value: Boolean){
        _dismiss.postValue(value)
    }
}