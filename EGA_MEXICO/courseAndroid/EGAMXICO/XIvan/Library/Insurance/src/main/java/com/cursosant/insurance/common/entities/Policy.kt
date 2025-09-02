package com.cursosant.insurance.common.entities

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
open class Policy(id: Long,
                  poliza_number: String,
                  start_of_validity: String?,
                  end_of_validity: String?,
                  automobiles_policy: List<PolicyCar>?,
                  damages_policy: List<PolicyDamage>?,
                  accidents_policy: List<PolicyAccident>?,
                  life_policy: List<PolicyLife>?,
                  val aseguradora: String? = null,
                  ramo: String?,
                  subramo: String?,
                  paquete: String?,
                  contractor: String?,
                  medico_telefono: PhoneDoctor?,
                  collection_executive: String?,
                  emision_date: String?,
                  natural: String?,
                  juridical: String?) : PolicyBase
    (id, poliza_number, start_of_validity, end_of_validity, automobiles_policy, damages_policy,
    accidents_policy, life_policy, ramo, subramo, paquete, contractor, medico_telefono,
    collection_executive, emision_date, natural, juridical)