package com.cursosant.insurance.docInsurersModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.docInsurersModule.model.DocInsurersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.docInsurersModule.viewModel
 * Created by Alain Nicolás Tello on 23/11/23 at 19
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
class DocsInsurersViewModel @Inject constructor(private val repository: DocInsurersRepository) : BaseViewModel(){
    private val _insurers = MutableLiveData<List<Insurance>>()
    val insurers: LiveData<List<Insurance>> = _insurers

    fun getInsurersBySubramoId(id: Long) {
        executeAction {
            repository.getInsurersBySubramoId(id){ result ->
                _insurers.postValue(result)
            }
        }
    }
}