package com.cursosant.insurance.signoutModule.model

import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.model.BaseRepository
import com.cursosant.insurance.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/****
 * Project: Contant
 * From: com.cursosandroidant.contant.logoutModule.model
 * Created by Alain Nicolás Tello on 03/03/23 at 16:28
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
class SignoutRepository @Inject constructor(private val dataSource: DataSource) : BaseRepository(){
    suspend fun signout(callback: (Boolean) -> Unit) = withContext(Dispatchers.IO){
        executeAction(InsuranceException(TypeError.SIGN_OUT)){
            dataSource.signout{
                //if (it) User.instance = null
                callback(it)
            }
        }
    }
}