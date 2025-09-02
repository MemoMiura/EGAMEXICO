package com.cursosant.insurance.common.entities

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 23/11/23 at 14
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
data class Subramo(val id: Long,
                   val subramo_name: String,
                   val subramo_code: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Subramo

        if (subramo_code != other.subramo_code) return false

        return true
    }

    override fun hashCode(): Int {
        return subramo_code
    }
}
