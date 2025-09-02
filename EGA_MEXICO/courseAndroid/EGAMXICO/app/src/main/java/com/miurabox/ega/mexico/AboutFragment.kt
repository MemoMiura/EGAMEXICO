package com.miurabox.ega.mexico

import com.cursosant.insurance.aboutModule.view.AboutFragment
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
class AboutFragment : AboutFragment() {
    override fun onStart() {
        super.onStart()
        setImgCover(R.drawable.fondo_nosotros)
        setAboutDescription(R.string.about_description)
        setWebsite("https://egamex.com.mx")
    }
}