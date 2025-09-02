package com.cursosant.insurance.notificationsModule.view.adapter

import com.cursosant.insurance.common.entities.Notification

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.notificationsModule.view.adapter
 * Created by Alain Nicolás Tello on 07/12/23 at 15:32
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
interface OnClickListener {
    fun onClick(notification: Notification)
}