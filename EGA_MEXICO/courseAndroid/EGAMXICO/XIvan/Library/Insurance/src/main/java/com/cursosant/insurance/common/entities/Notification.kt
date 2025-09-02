package com.cursosant.insurance.common.entities

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 14/11/23 at 2:39 PM
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
data class Notification(val id: Long,
                        val title: String,
                        val description: String,
                        val site: String?,
                        val image: List<FileImage>?) {
    fun getImgUrl(): String? {
        return if (!image.isNullOrEmpty()) image[0].arch else null
    }
}
