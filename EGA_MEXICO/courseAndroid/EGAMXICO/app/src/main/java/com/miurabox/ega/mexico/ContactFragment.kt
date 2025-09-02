package com.miurabox.ega.mexico

import com.cursosant.insurance.contactModule.view.ContactFragment
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
class ContactFragment : ContactFragment() {
    override fun onStart() {
        super.onStart()
        setImgCover(R.drawable.img_map_office)
        setAddress(getString(R.string.contact_address))
        setLatLong(25.6270123,-100.3138775)
    }
}