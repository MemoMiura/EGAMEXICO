package com.cursosant.insurance.insurersModule.model

import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.utils.TemporalData
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.insurersModule.model
 * Created by Alain Nicolás Tello on 02/06/23 at 19:35
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
class DataSource @Inject constructor(private val service: MiuraboxService){
    suspend fun getInsurersByUser(token: String, username: String): List<Insurance>{
        return service.getInsurersByUser(token, username)
    }

    fun getInsurersBySubramoId(id: Long) : List<Insurance> {
        val result = mutableListOf<Insurance>()
        TemporalData.getAncoraDocs().forEach {
            if (it.subramo.id == id && !result.contains(it.aseguradora)) {//!result.contains(it.aseguradora)) {
                result.add(it.aseguradora)
            }
        }
        return result
    }
    fun getInsurersBySubramoCode(code: Int) : List<Insurance> {
        val result = mutableListOf<Insurance>()
        TemporalData.getAncoraDocs().forEach {
            if (it.subramo.subramo_code == code && !result.contains(it.aseguradora)) {//!result.contains(it.aseguradora)) {
                result.add(it.aseguradora)
            }
        }
        return result
    }
}