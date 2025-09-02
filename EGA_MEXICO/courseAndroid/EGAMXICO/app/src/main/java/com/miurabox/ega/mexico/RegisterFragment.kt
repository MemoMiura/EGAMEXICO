package com.miurabox.ega.mexico

import com.cursosant.insurance.registerModule.view.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

/****
 * Project: ega.mexico
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
class RegisterFragment : RegisterFragment(){
    override fun onStart() {
        super.onStart()
        //setImgCover(R.drawable.fondo)
        //setBackgroundColor(R.color.color_primary, R.color.color_on_primary)
    }
}