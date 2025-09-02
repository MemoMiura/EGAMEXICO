package com.cursosant.insurance.common.utils

import java.text.NumberFormat
import java.util.Locale

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.common.utils
 * Created by Alain Nicolás Tello on 02/08/23 at 16:53
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
object FormatUtils {
    fun formatCurrency(value: String?): String? {
        return if (value?.toDoubleOrNull() != null) {
            if (!value.contentEquals("0")) {
                var valueFormat = ""
                val format = NumberFormat.getCurrencyInstance(Locale.US)
                valueFormat = format.format(java.lang.Double.valueOf(value))
                valueFormat
            } else {
                "0"//si es cero lo regresa sin formato
            }
        } else {
            value//si no es numero regresa la misma cadena
        }
    }
}