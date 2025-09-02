package com.cursosant.insurance.common.entities

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 02/06/23 at 16:41
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
data class PolicyDamage(val insured_item: String? = null,
                        val sucursal: String? = null,
                        val giro: String? = null,
                        val type_construction: Int? = null,
                        val no_levels: Int? = null,
                        val fhm_zone: String? = null,
                        val tyev_zone: String? = null,
                        val dam_owner: String? = null,
                        val no_employee: String? = null,
                        val usage: String? = null,
                        val brand: String? = null,
                        val model: String? = null,
                        val construction_year: String? = null,
                        val reconstruction_year: String? = null,
                        val version: String? = null,
                        val serial: String? = null,
                        val engine: String? = null,
                        val color: String? = null,
                        val matricula: String? = null,
                        val permiso: String? = null,
                        val purchase_value: String? = null,
                        val email: String? = null,
                        val item_address: String? = null,
                        val item_details: String? = null) {
}