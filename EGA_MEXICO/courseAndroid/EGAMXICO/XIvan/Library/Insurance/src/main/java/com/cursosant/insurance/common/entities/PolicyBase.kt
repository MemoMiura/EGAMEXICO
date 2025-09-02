package com.cursosant.insurance.common.entities

import com.cursosant.insurance.common.utils.Constants

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.entities
 * Created by Alain Nicolás Tello on 04/06/23 at 13:54
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
open class PolicyBase(val id: Long,
                      val poliza_number: String? = null,
                      val start_of_validity: String? = null,
                      val end_of_validity: String? = null,
                      val automobiles_policy: List<PolicyCar>?,
                      val damages_policy: List<PolicyDamage>?,
                      val accidents_policy: List<PolicyAccident>?,
                      val life_policy: List<PolicyLife>?,
                      val ramo: String? = null,
                      val subramo: String? = null,//type
                      val paquete: String? = null,
                      val contractor: String? = null,
                      val medico_telefono: PhoneDoctor? = null,
                      val collection_executive: String? = null,
                      val emision_date: String? = null,
                      val natural: String? = null,
                      val juridical: String? = null) {

    fun getDescription(): String {
        return when(subramo){
            Constants.SUBRAMO_CAR -> {
                with(automobiles_policy?.get(0)){
                    "${this?.brand} ${this?.model} ${this?.year}"
                }
            }
            Constants.SUBRAMO_MEDIC_EXPENSES,
            Constants.SUBRAMO_HEALTH -> {
                with(accidents_policy?.get(0)?.personal){
                    "${this?.getFullName()}"
                }
            }
            Constants.SUBRAMO_LIFE -> {
                with(life_policy?.get(0)?.personal){
                    "${this?.getFullName()}"
                }
            }
            else -> {
                with(damages_policy?.get(0)){
                    "${this?.insured_item}"
                }
            }
        }
    }

    fun getCurrency() = "MXN" // FIXME: incomplete

    fun getBeneficiaries(): List<LifeBeneficiary> = if (!life_policy.isNullOrEmpty())
            life_policy[0].beneficiaries_life
        else if (!accidents_policy.isNullOrEmpty())
            accidents_policy[0].relationship_accident
        else listOf()
}