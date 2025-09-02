package com.cursosant.insurance.common.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 16/06/23 at 17:07
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
@Parcelize
data class QuoteResponse(val client_id: Long?,
                         val form_data_id: Long?,
                         val services: List<Int>?) : Parcelable
/*data class QuoteResponse(val client_id: Long?,
                         val form_data_id: Long?,
                         val services: List<Int>?)*/
