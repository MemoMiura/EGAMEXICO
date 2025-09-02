package com.cursosant.insurance.common.entities

import com.cursosant.insurance.common.utils.Constants

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 02/06/23 at 12:21
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
class PolicyDetail(id: Long,
                   poliza_number: String,
                   start_of_validity: String?,
                   end_of_validity: String?,
                   val files: List<FilePDF>,
                   val coverageInPolicy_policy: List<PolicyCoverage>,
                   automobiles_policy: List<PolicyCar>?,
                   damages_policy: List<PolicyDamage>?,
                   accidents_policy: List<PolicyAccident>?,
                   life_policy: List<PolicyLife>?,
                   val aseguradora: Aseguradora?,
                   ramo: String?,
                   subramo: String?,
                   paquete: String?,
                   val recibos_poliza: List<PolicyReceipt>?,
                   contractor: String?,
                   medico_telefono: PhoneDoctor?,
                   collection_executive: String?,
                   emision_date: String?,
                   natural: String?,
                   juridical: String?) : PolicyBase
    (id, poliza_number, start_of_validity, end_of_validity, automobiles_policy, damages_policy,
    accidents_policy, life_policy, ramo, subramo, paquete, contractor, medico_telefono,
    collection_executive, emision_date, natural, juridical){
    fun getPolicyCar(): PolicyCar = if (automobiles_policy.isNullOrEmpty()) PolicyCar() else automobiles_policy[0]

    fun getDamage(): PolicyDamage {
        return if (damages_policy.isNullOrEmpty()) PolicyDamage()
        else damages_policy[0]
    }

    fun getPersonal(): Personal = if (!accidents_policy.isNullOrEmpty())
            accidents_policy[0].personal
        else if (!life_policy.isNullOrEmpty())
            life_policy[0].personal
        else Personal()

    fun getPhone(): String? {
        return when(this.subramo) {
            Constants.SUBRAMO_MEDIC_EXPENSES -> this.medico_telefono?.medico_telefono
            else -> this.aseguradora?.phone
        }
    }
    fun getDoctorName(): String? {
        return when(this.subramo) {
            Constants.SUBRAMO_MEDIC_EXPENSES -> this.medico_telefono?.medico
            else -> null
        }
    }
}
