package com.cursosant.insurance.common.entities

import android.content.Context
import com.cursosant.insurance.R

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 12/06/23 at 12:21
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
data class LifeBeneficiary(val id: Long,
                           val first_name: String? = null,
                           val last_name: String? = null,
                           val second_last_name: String? = null,
                           val birthdate: String? = null,
                           val sex: String? = null,
                           val optional_relationship: String? = null,
                           val full_name: String? = null) {
    fun getFullName() = full_name ?: "$first_name $last_name $second_last_name"

    fun getGender(context: Context) =
        if (sex == "M") context.getString(R.string.common_gender_male)
        else context.getString(R.string.common_gender_female)
}