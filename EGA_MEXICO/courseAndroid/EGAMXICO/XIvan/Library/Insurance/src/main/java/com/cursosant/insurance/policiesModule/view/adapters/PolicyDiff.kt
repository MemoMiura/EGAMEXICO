package com.cursosant.insurance.policiesModule.view.adapters

import androidx.recyclerview.widget.DiffUtil
import com.cursosant.insurance.common.entities.Policy
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policiesModule.view.adapters
 * Created by Alain Nicolás Tello on 02/06/23 at 12:25
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
class PolicyDiff @Inject constructor() : DiffUtil.ItemCallback<Policy>() {
    override fun areItemsTheSame(oldItem: Policy, newItem: Policy) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Policy, newItem: Policy) = oldItem == newItem
}