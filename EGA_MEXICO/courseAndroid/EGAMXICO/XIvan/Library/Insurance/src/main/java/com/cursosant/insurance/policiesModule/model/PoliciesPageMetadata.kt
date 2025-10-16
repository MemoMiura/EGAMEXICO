package com.cursosant.insurance.policiesModule.model

/**
 * Información básica de paginación que permite a la capa de UI conocer
 * el estado actual del listado (página, totales y navegación disponible).
 */
data class PoliciesPageMetadata(
    val currentPage: Int,
    val totalPages: Int,
    val totalCount: Int,
    val hasPrevious: Boolean,
    val hasNext: Boolean
)
