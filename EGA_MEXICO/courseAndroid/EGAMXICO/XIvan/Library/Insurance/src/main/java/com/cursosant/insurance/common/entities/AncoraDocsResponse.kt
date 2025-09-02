package com.cursosant.insurance.common.entities

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 23/11/23 at 14
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
data class AncoraDocsResponse(val id: Long,
                              val url: String,
                              val org_name: String,
                              val arch: String,
                              val nombre: String,
                              val subramo: Subramo,
                              val aseguradora: Insurance) {
}