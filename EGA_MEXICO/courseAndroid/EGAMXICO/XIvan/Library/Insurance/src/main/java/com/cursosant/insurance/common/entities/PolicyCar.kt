package com.cursosant.insurance.common.entities

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 02/06/23 at 16:07
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
data class PolicyCar(val brand: String? = null,
                     val model: String? = null,
                     val year: Int? = null,
                     val usage: Int? = null,
                     val service: String? = null,
                     val policy_type: Int? = null,
                     val procedencia: Int? = null,
                     val charge_type: Int? = null,
                     val preferential_benefiaciary: String? = null,
                     val car_owner: String? = null,
                     val no_employee: String? = null,
                     val color: String? = null,
                     val license_plates: String? = null,
                     val version: String? = null,
                     val serial: String? = null,
                     val engine: String? = null)
