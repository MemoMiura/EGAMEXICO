package com.cursosant.insurance.common.entities

import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.FormatUtils

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 06/06/23 at 10:32
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
data class PolicyCoverage(val coverage_name: String?,
                          val sum_insured: String?,
                          val deductible: String?,
                          val coinsurance: String?,
                          val topecoinsurance: String?,
                          val priority: Int?,
                          var subramo: String?,
                          var idIndex: Int = 0,
                          val sum_coverage: String?,
                          val deductible_coverage: String?) {
    fun getDeductibleValid(): String = deductible ?: (deductible_coverage ?: Constants.COVERAGE_DEDUCTIBLE_DEFAULT)

    fun getSum(): String? = FormatUtils.formatCurrency(sum_insured ?: sum_coverage)

    fun getSubBouquet(): String = subramo ?: Constants.SUBRAMO_CAR
}