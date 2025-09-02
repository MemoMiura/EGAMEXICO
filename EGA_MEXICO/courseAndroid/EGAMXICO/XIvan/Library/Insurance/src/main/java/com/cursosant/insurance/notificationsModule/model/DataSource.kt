package com.cursosant.insurance.notificationsModule.model

import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.Notification
import com.cursosant.insurance.common.entities.NotificationResponse
import com.cursosant.insurance.common.utils.Constants
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.notificationsModule.model
 * Created by Alain Nicolás Tello on 14/11/23 at 2:29 PM
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
class DataSource @Inject constructor(private val service: MiuraboxService) {
    suspend fun getNotificationsByUser(token: String, username: String): NotificationResponse {
    //suspend fun getNotificationsByUser(token: String, username: String): Any{//List<Notification> {
        //return service.getNotificationsByUser3("${Constants.H_BEARER}$token", username)
        return service.getNotificationsByUser(token, username)
    }
}