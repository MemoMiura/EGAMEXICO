package com.cursosant.insurance.docInsurersModule.view

import android.os.Bundle
import android.widget.Toast
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.entities.User
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.NavUtils
import com.cursosant.insurance.insurersModule.view.InsurersFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.docInsurersModule.view
 * Created by Alain Nicolás Tello on 24/11/23 at 10
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
@AndroidEntryPoint
class DocInsurersFragment : InsurersFragment() {

    @Inject
    lateinit var navUtils: NavUtils

    override fun onClick(insurance: Insurance) {
        navUtils.run {
            val args = Bundle()
            args.putBoolean(Constants.ARG_IS_ANCORA, true)
            args.putLong(Constants.ARG_ID, insurance.id)
            args.putInt(Constants.ARG_CODE, subramoCode)
            navController.navigate(actionDocInsurersToDocuments, args)
        }
    }
}