package com.cursosant.insurance.signoutModule.viewModel

import com.cursosant.insurance.common.viewModel.DialogViewModel
import com.cursosant.insurance.signoutModule.model.SignoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Contant
 * From: com.cursosandroidant.contant.logoutModule.viewModel
 * Created by Alain Nicolás Tello on 03/03/23 at 16:42
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
class SignoutViewModel @Inject constructor(private val repository: SignoutRepository) : DialogViewModel() {
    fun signout(){
        executeAction {
            repository.signout{ result ->
                if (result) setDismiss(true)
            }
        }
    }
}