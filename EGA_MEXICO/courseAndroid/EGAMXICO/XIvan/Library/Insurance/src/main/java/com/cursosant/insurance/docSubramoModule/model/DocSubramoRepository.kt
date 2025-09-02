package com.cursosant.insurance.docSubramoModule.model

import com.cursosant.insurance.common.entities.AncoraDocsResponse
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.Subramo
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
class DocSubramoRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository() {
    suspend fun getSubramosByUsername(token: String, username: String, callback: (List<Subramo>) -> Unit) =
        withContext(Dispatchers.IO) {
            executeAction(InsuranceException(TypeError.SUBRAMOS)) {
                val result = dataSource.getAncoraDocsByUsername(token, username)
                dataSource.saveAncoraDocs(result)
                callback(dataSource.getSubramos())
            }
        }
}