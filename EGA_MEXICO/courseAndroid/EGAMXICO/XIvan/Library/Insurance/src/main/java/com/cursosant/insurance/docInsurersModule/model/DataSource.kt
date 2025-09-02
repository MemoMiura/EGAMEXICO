package com.cursosant.insurance.docInsurersModule.model

import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.utils.TemporalData
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.docInsuranceModule.model
 * Created by Alain Nicolás Tello on 23/11/23 at 19
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
    suspend fun getInsurersBySubramoId(id: Long) : List<Insurance> {
        val result = mutableListOf<Insurance>()
        TemporalData.getAncoraDocs().forEach {
            if (!result.contains(it.aseguradora)) result.add(it.aseguradora)
        }
        return result
    }
}