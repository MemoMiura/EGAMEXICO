package com.miurabox.ega.mexico

import com.cursosant.insurance.homeModule.view.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

/****
 * Project: EGAMexico
 * From: com.miurabox.egamexico
 * Created by Iván Nicolás Tello on 31/08/23 at 08:15
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
class HomeFragment : HomeFragment() {
    override fun onStart() {
        super.onStart()
        setImgCover(R.drawable.fondo_menu)
        setBackgroundColor(R.color.color_custom_background)
    }
}