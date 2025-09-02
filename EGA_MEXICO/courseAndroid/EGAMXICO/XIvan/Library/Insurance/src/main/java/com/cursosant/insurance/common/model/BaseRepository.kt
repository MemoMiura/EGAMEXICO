package com.cursosant.insurance.common.model

import com.cursosant.insurance.common.entities.InsuranceException
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.model
 * Created by Alain Nicolás Tello on 31/05/23 at 19:16
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
open class BaseRepository @Inject constructor() {
    suspend fun executeAction(exception: InsuranceException, block: suspend () -> Unit) {
        try {
            block()
        } catch (e: Exception){
            throw exception
        }
    }
}