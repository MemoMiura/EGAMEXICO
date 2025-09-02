package com.cursosant.insurance.insurersModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.insurersModule.model
 * Created by Alain Nicolás Tello on 03/06/23 at 11:51
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
class InsurersRepository @Inject constructor(private val dataSource: DataSource): BaseRepository(){
    suspend fun getInsurers(token: String, username: String, callback: (List<Insurance>) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.INSURERS)){
                val result = dataSource.getInsurersByUser(token, username)
                callback(result)
            }
        }

    suspend fun getInsurersBySubramoId(id: Long, callback: (List<Insurance>) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.SUBRAMOS)){
                callback(dataSource.getInsurersBySubramoId(id))
            }
        }

    suspend fun getInsurersBySubramoCode(code: Int, callback: (List<Insurance>) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.SUBRAMOS)){
                callback(dataSource.getInsurersBySubramoCode(code))
            }
        }
}