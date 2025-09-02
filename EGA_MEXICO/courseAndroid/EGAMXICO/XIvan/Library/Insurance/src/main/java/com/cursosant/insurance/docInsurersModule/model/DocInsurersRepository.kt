package com.cursosant.insurance.docInsurersModule.model

import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.docInsurersModule.model
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
class DocInsurersRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository(){
    suspend fun getInsurersBySubramoId(id: Long, callback: (List<Insurance>) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.SUBRAMOS)){
                callback(dataSource.getInsurersBySubramoId(id))
            }
        }
}