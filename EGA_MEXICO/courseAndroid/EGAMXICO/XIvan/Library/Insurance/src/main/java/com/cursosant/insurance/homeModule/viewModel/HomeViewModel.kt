package com.cursosant.insurance.homeModule.viewModel

import androidx.lifecycle.liveData
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.homeModule.model.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.homeModule.viewModel
 * Created by Alain Nicolás Tello on 03/01/24 at 18:43
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
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel() {
    val initialSetupEvent = liveData {
        emit(repository.fetchInitialPreferences())
    }
}