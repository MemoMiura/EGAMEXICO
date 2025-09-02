package com.cursosant.insurance.docSubramoModule.model

import android.util.Log
import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.AncoraDocsResponse
import com.cursosant.insurance.common.entities.Subramo
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.TemporalData
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.docSubramoModule.model
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
class DataSource @Inject constructor(private val service: MiuraboxService) {
    suspend fun getAncoraDocsByUsername(token: String, username: String) : List<AncoraDocsResponse> {
        /*val result = service.getAncoraDocsByUsernameAny("${Constants.H_BEARER}$token", username)
        if (result != null) {
            Log.i("TAG", "getAncoraDocsByUsername: ${result}")
        }
        return result as List<AncoraDocsResponse>*/
        return service.getAncoraDocsByUsername("${Constants.H_BEARER}$token", username)
    }

    fun saveAncoraDocs(list: List<AncoraDocsResponse>) {
        TemporalData.addAncoraDocs(list)
    }

    fun getSubramos(): List<Subramo> {
        val result = mutableListOf<Subramo>()
        TemporalData.getAncoraDocs().forEach {
            if (!result.contains(it.subramo)) result.add(it.subramo)
        }
        return result
    }
}