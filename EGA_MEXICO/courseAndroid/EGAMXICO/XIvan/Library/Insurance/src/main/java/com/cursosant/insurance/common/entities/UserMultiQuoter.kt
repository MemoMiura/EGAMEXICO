package com.cursosant.insurance.common.entities

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 30/05/23 at 12:51
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
data class UserMultiQuoter(val first_name: String = "",
                           val last_name: String = "",
                           var token: String = "",
                           /*val rol: Int = 0,
                           val user_id: Long = 0,
                           val username: String = "",
                           val email: String = "",
                           val caratula: String = "",
                           val is_superuser: Boolean = false,*/
                           val mc: String = "",
                           val error: String? = null) {
    /*val token_multicotizador: String = "" ???
    val password: String = "" ?? cambia el token?
    val full_name: String = ""
    val quote: String? = null*/
}