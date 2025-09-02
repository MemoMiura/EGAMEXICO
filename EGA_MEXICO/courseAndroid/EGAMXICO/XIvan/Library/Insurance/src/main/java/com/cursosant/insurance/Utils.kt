package com.cursosant.insurance

import java.util.regex.Pattern
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.common.utils
 * Created by Alain Nicolás Tello on 05/07/23 at 12:21
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
class Utils @Inject constructor() {
    private val validEmailAddressRegex =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    fun validateEmail(email: String?): Boolean {
        return email != null && validEmailAddressRegex.matcher(email).find()
    }
}