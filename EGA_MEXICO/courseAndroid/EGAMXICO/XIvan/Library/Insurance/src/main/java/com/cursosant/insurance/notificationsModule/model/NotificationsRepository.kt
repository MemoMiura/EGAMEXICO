package com.cursosant.insurance.notificationsModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.Notification
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.notificationsModule.model
 * Created by Alain Nicolás Tello on 14/11/23 at 2:44 PM
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
class NotificationsRepository @Inject constructor(private val dataSource: DataSource, private val dataStore: NotificationsDataStore) : BaseRepository(){
    suspend fun getNotificationsByUser(token: String, username: String, callback: (List<Notification>) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.NOTIFICATIONS)){
                val result = dataSource.getNotificationsByUser(token, username)
                callback(result.data)
            }
        }

    suspend fun updatePreferences(isThereNotify: Boolean) {
        dataStore.updatePreferences(isThereNotify)
    }
}