package com.cursosant.insurance.common.entities

import com.cursosant.insurance.common.utils.Constants

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 14/06/23 at 12:21
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
data class QuotePayFrequency(val id: Long? = null,
                             val first_pay: String? = null,
                             val tipe: String? = null,
                             val second_pay: String? = null,
                             val total_amount: String? = null,
                             val fecha_inicio: String? = null,
                             val fecha_fin: String? = null,
                             var description: String = "")