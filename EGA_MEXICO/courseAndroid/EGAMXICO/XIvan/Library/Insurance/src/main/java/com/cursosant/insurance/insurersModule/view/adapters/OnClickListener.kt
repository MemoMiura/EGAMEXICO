package com.cursosant.insurance.insurersModule.view.adapters

import com.cursosant.insurance.common.entities.Insurance

/****
 * Project: Insurance
 * From: com.cursosant.insurance.insurersModule.view.adapters
 * Created by Alain Nicolás Tello on 05/07/23 at 11:36
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
    fun onClick(insurance: Insurance)
}