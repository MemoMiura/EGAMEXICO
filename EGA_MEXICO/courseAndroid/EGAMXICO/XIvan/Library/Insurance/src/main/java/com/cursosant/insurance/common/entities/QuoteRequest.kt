package com.cursosant.insurance.common.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 07/08/23 at 12:57
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
data class QuoteRequest(var gender: String? = null,
                        var birthdate: String? = null,
                        var typeId: Int = 0,
                        var brand: String? = null,
                        var year: String? = null,
                        var modelId: String? = null,
                        var model: String? = null,
                        var cp: String? = null,
                        var stateId: Long? = null,
                        var state: String? = null,
                        var city: String? = null,
                        var suburb: String? = null,
                        var age: Int? = null) : Parcelable
