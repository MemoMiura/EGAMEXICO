package com.cursosant.insurance.common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.DialogFragment
import com.cursosant.insurance.R
import java.util.regex.Pattern
import javax.inject.Inject

/****
 * Project: Insurance
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
class Utils @Inject constructor(private val uiUtils: UiUtils) {
    private val validEmailAddressRegex =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    fun validateEmail(email: String?): Boolean {
        return email != null && validEmailAddressRegex.matcher(email).find()
    }

    //Report Sinister
    fun checkPhone(view: View, phone: String?, name: String? = null, dialog: DialogFragment? = null) {
        if (!phone.isNullOrBlank()) {
            uiUtils.snackbarAction(view,
                if (name != null) "$name: $phone" else view.context.getString(R.string.sinister_call_msg, phone),
                R.string.sinister_call){ call(view, phone, view.context, dialog) }
        } else {
            uiUtils.snackbarAction(view, view.context.getString(R.string.sinister_no_phone),
                R.string.snackbar_ok){ dialog?.dismiss() }
        }
    }

    private fun call(view: View, phone: String, context: Context, dialog: DialogFragment? = null) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phone")
        if (callIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(callIntent)
            dialog?.dismiss()
        } else uiUtils.snackbarLong(view, R.string.call_no_app_resolve)
    }
}