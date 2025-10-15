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
    @SerializedName("results") private val results: List<Policy>? = null,
    @SerializedName("policies") private val policies: List<Policy>? = null,
    @SerializedName("polizas") private val legacyPolicies: List<Policy>? = null,
    @SerializedName("data") private val dataPolicies: List<Policy>? = null
) {
    val items: List<Policy>
        get() = results
            ?: policies
            ?: legacyPolicies
            ?: dataPolicies
            ?: emptyList()
}
