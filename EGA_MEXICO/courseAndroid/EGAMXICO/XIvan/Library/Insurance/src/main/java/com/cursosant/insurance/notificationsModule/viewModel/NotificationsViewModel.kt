package com.cursosant.insurance.notificationsModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.Notification
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.notificationsModule.model.NotificationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.notificationsModule.viewModel
 * Created by Alain Nicolás Tello on 05/09/23 at 11:26
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
class NotificationsViewModel @Inject constructor(private val repository: NotificationsRepository) : BaseViewModel() {
    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> = _notifications

    fun getNotificationsByUser(token: String, username: String){
        executeAction {
            repository.getNotificationsByUser(token, username){ result ->
                _notifications.postValue(result)
            }
        }
    }

    fun updatePreferences(isThereNotify: Boolean) {
        executeAction { repository.updatePreferences(isThereNotify) }
    }
}