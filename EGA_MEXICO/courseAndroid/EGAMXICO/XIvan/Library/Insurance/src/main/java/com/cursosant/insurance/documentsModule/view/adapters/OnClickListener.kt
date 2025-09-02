package com.cursosant.insurance.documentsModule.view.adapters

import com.cursosant.insurance.common.entities.Document

/****
 * Project: Insurance
 * From: com.cursosant.insurance.documentsModule.view.adapters
 * Created by Alain Nicolás Tello on 07/06/23 at 15:35
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
interface OnClickListener {
    fun onClick(document: Document)
}