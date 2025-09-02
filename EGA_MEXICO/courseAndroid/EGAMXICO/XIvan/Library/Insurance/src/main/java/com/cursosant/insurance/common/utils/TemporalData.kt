package com.cursosant.insurance.common.utils

import com.cursosant.insurance.common.entities.AncoraDocsResponse

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.common.utils
 * Created by Alain Nicolás Tello on 23/11/23 at 18
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
object TemporalData {
    private var ancoraDocs: MutableList<AncoraDocsResponse> = mutableListOf()

    fun addAncoraDocs(list: List<AncoraDocsResponse>) {
        ancoraDocs.clear()
        ancoraDocs.addAll(list)
    }

    fun getAncoraDocs() : MutableList<AncoraDocsResponse> = ancoraDocs
}