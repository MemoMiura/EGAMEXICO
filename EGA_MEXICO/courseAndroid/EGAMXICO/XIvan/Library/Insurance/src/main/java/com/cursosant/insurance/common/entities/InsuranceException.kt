package com.cursosant.insurance.common.entities

import com.cursosant.insurance.common.utils.TypeError

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 31/05/23 at 11:36
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
data class InsuranceException(var typeError: TypeError): Exception() {
}