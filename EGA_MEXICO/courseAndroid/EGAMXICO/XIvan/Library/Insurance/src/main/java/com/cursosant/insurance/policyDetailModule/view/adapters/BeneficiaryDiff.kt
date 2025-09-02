package com.cursosant.insurance.policyDetailModule.view.adapters

import androidx.recyclerview.widget.DiffUtil
import com.cursosant.insurance.common.entities.LifeBeneficiary
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policyDetailModule.view.adapters
 * Created by Alain Nicolás Tello on 06/06/23 at 12:06
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
class BeneficiaryDiff @Inject constructor() : DiffUtil.ItemCallback<LifeBeneficiary>() {
    override fun areItemsTheSame(oldItem: LifeBeneficiary, newItem: LifeBeneficiary) = newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: LifeBeneficiary, newItem: LifeBeneficiary) = newItem == oldItem
}