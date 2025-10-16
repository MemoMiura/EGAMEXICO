package com.cursosant.insurance.policiesModule.model

import javax.inject.Inject

/**
 * Encapsula la lógica de consulta paginada de pólizas.
 *
 * Aunque el módulo ya no usa directamente la librería de Paging3, se mantiene esta clase
 * para centralizar la forma en que se normaliza la página solicitada y se delega al
 * [DataSource] real. De esta manera el repositorio puede seguir solicitando páginas
 * específicas (inicial o subsecuentes) tal como lo hace SegumovilApp.
 */
class PoliciesPagingSource @Inject constructor(private val dataSource: DataSource) {

    suspend fun load(
        token: String,
        username: String,
        page: Int?,
        pageSize: Int
    ): PolicyPagedResponse {
        val normalizedPage = page?.takeIf { it > 0 } ?: FIRST_PAGE
        val normalizedPageSize = if (pageSize > 0) pageSize else DEFAULT_PAGE_SIZE

        return dataSource.getPolicies(
            token = token,
            username = username,
            page = normalizedPage,
            pageSize = normalizedPageSize
        )
    }

    companion object {
        const val FIRST_PAGE = 1
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
