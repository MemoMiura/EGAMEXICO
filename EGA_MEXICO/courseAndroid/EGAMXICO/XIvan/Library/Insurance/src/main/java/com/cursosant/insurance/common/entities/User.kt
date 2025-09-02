package com.cursosant.insurance.common.entities

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 27/05/23 at 12:36
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
data class User(val first_name: String = "",
                val last_name: String = "",
                //val org_url: String = "",
                val org_logo: String = "",
                val username: String = "",
                var user: Long = 0,
                val phone: String = "",
                val second_last_name: String = "",
                val rfc: String = "",
                //val org_name: String = "",
                val email: String = "",
                //val org_address: String = "",
                val token: Token = Token(),
                val birthdate: String = "",
                val gender:Int = 1,
                var passwordUser: String? = null,
                var tokenMultiQuoteUser: String? = null){

    fun getPassword() = passwordUser ?: ""

    fun getTokenMultiQuote() = tokenMultiQuoteUser ?: ""

    companion object{
        var instance: User? = null
    }
}
