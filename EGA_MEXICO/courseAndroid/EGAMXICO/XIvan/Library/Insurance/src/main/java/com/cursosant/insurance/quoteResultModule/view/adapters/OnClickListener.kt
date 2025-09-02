package com.cursosant.insurance.quoteResultModule.view.adapters

import com.cursosant.insurance.common.entities.ServiceResponse

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.quoteModule.view.adapters
 * Created by Alain Nicolás Tello on 22/07/23 at 17:44
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
    fun onClick(serviceResponse: ServiceResponse)
}