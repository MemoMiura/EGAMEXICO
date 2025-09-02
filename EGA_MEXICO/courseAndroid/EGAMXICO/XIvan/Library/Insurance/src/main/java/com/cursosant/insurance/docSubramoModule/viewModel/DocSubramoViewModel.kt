package com.cursosant.insurance.docSubramoModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.Subramo
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.docSubramoModule.model.DocSubramoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.docSubramoModule.viewModel
 * Created by Alain Nicolás Tello on 23/11/23 at 13
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
class DocSubramoViewModel @Inject constructor(private val repository: DocSubramoRepository) : BaseViewModel() {
    private val _subramos = MutableLiveData<List<Subramo>>()
    val subramos: LiveData<List<Subramo>> = _subramos

    fun getSubramosByUsername(token: String, username: String){
        executeAction {
            repository.getSubramosByUsername(token, username){ result ->
                _subramos.postValue(result)
            }
        }
    }
}