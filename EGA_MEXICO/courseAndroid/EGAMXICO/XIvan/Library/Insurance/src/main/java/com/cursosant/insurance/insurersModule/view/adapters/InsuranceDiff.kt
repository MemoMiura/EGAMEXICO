package com.cursosant.insurance.insurersModule.view.adapters

import androidx.recyclerview.widget.DiffUtil
import com.cursosant.insurance.common.entities.Insurance
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.insurersModule.view.adapters
 * Created by Alain Nicolás Tello on 03/06/23 at 11:07
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
class InsuranceDiff @Inject constructor() : DiffUtil.ItemCallback<Insurance>() {
    override fun areItemsTheSame(oldItem: Insurance, newItem: Insurance) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Insurance, newItem: Insurance) = oldItem == newItem
}