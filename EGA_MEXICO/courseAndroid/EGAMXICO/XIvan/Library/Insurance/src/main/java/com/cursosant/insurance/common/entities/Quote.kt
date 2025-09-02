package com.cursosant.insurance.common.entities

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 14/06/23 at 12:15
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
data class Quote(val id: Long?,
                 val name: String?,
                 val quote_pay_frecuency: List<QuotePayFrequency>?,
                 var type: String = "", var description: String = "") {
    fun getTotalAmount(): String {
        val total = ""
        try {
            when(type) {
                "Contado" -> {
                    "${quote_pay_frecuency?.first { it.tipe == "Contado" }?.total_amount}"
                    description = "Prima total"
                }
                "Mensual" -> {
                    "Total: ${quote_pay_frecuency?.first { it.tipe == "Mensual" }?.total_amount}"
                    description = "1"
                }
                "Trimestral" -> {
                    "Total: ${quote_pay_frecuency?.first { it.tipe == "Trimestral" }?.total_amount}"
                    description = "3"
                }
                "Semestral" -> {
                    "Total: ${quote_pay_frecuency?.first { it.tipe == "Semestral" }?.total_amount}"
                    description = "6"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return total
    }
}
