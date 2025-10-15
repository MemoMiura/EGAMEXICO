package com.cursosant.insurance.common.entities

import com.google.gson.annotations.SerializedName

/**
 * Representa una página de resultados de pólizas usando el esquema legado del módulo.
 */
data class PoliciesPage(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName(value = "policies", alternate = ["results"])
    val policies: List<Policy> = emptyList()
)
