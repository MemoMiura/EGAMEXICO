package com.cursosant.insurance.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.cursosant.insurance.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/****
 * Project: Xpenses
 * From: com.cursosant.insurance.common.utils
 * Created by Alain Nicolás Tello on 04/05/23 at 13:34
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
class UiUtils @Inject constructor(@ApplicationContext val context: Context) {
    private var snackbar: Snackbar? = null

    fun hideKeyboard(view: View){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun snackbarShort(view: View, resMsg: Int) {
        createSnackbar(view, resMsg, Snackbar.LENGTH_SHORT)
    }

    fun snackbarLong(view: View, resMsg: Int) {
        createSnackbar(view, resMsg, Snackbar.LENGTH_LONG)
    }

    fun snackbarWarning(view: View, resMsg: Int) {
        createSnackbar(view, resMsg, Snackbar.LENGTH_INDEFINITE, true)
    }

    private fun createSnackbar(view: View, resMsg: Int, duration: Int, isIndeterminate: Boolean = false) {
        snackbar = null
        snackbar = Snackbar.make(view, resMsg, duration)
        if (isIndeterminate) snackbar?.setAction(R.string.snackbar_ok){}
        snackbar?.show()
    }

    fun snackbarAction(view: View, msg: String, resAction: Int, block: () -> Unit) {
        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
        snackbar?.setAction(resAction){ block() }
        ?.show()
    }

    fun clearSnackbar() = snackbar?.dismiss()

    fun toastLong(resMsg: Int){
        Toast.makeText(context, resMsg, Toast.LENGTH_LONG).show()
    }
}