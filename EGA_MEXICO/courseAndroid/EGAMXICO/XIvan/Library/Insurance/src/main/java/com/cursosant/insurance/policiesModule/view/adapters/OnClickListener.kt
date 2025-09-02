package com.cursosant.insurance.policiesModule.view.adapters

import com.cursosant.insurance.common.entities.Policy

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policiesModule.view.adapters
 * Created by Alain Nicolás Tello on 02/06/23 at 12:28
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
    fun onClick(policy: Policy)
}