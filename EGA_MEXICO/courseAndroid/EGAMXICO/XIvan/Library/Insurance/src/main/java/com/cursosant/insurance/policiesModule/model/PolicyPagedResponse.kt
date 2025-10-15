package com.cursosant.insurance.policiesModule.model

import com.cursosant.insurance.common.entities.Policy
import com.google.gson.annotations.SerializedName

/**
 * Representa la estructura de respuesta paginada para las pólizas.
 */
data class PolicyPagedResponse(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName(value = "results", alternate = ["policies", "polizas"])
    val results: List<Policy>? = emptyList(),
    @SerializedName("page_size") val pageSize: Int? = null
)
