package com.cursosant.insurance.contactModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.ContactResponse
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.contactModule.module.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.contactModule.viewModel
 * Created by Alain Nicolás Tello on 08/06/23 at 15:55
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
class ContactViewModel @Inject constructor(private val repository: ContactRepository) : BaseViewModel() {
    fun sendMessage(token: String, name: String, email: String, message: String){
        executeAction {
            repository.sendMessage(token, name, email, message) {
                showMsg(R.string.contact_send_message_success)
            }
        }
    }
}