package com.cursosant.insurance.notificationsModule.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cursosant.insurance.common.entities.Notification
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.documentsModule.view
 * Created by Alain Nicolás Tello on 07/06/23 at 15:16
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
class NotificationDiff @Inject constructor() : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification) = newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification) = newItem == oldItem
}