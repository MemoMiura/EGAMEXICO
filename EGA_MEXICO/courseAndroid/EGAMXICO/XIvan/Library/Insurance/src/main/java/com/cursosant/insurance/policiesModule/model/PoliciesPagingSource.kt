package com.cursosant.insurance.policiesModule.model

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.utils.Constants
import java.io.IOException
import kotlin.math.max
import retrofit2.HttpException

class PoliciesPagingSource(
    private val dataSource: DataSource,
    private val token: String,
    private val pageSize: Int,
    private val manualNavigation: Boolean = false,
    private val onPageMetadata: (PoliciesPageMetadata) -> Unit = {}
) : PagingSource<Int, Policy>() {

    /**
     * Paging 3 utiliza esta clave para intentar volver a cargar datos cercanos al ítem
     * que el usuario está visualizando después de una invalidación.
     */
    override fun getRefreshKey(state: PagingState<Int, Policy>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Policy> {
        val page = params.key ?: FIRST_PAGE
        return try {
            // Realizamos la petición HTTP utilizando Retrofit a través del DataSource.
            val response = dataSource.getPolicies(token, page, pageSize)

            // Extraemos la lista de pólizas desde el campo "results" de la respuesta paginada.
            val policies = response.results.orEmpty()

            // Calculamos las claves de navegación basándonos en los enlaces "next" y "previous".
            val nextKey = resolveNextKey(response, page, policies.size)
            val prevKey = resolvePreviousKey(response, page)

            notifyMetadata(response, page)

            LoadResult.Page(
                data = policies,
                prevKey = if (manualNavigation) null else prevKey,
                nextKey = if (manualNavigation) null else nextKey
            )
        } catch (ioException: IOException) {
            // Errores de red (sin conexión, timeouts, etc.).
            LoadResult.Error(ioException)
        } catch (httpException: HttpException) {
            // Errores HTTP (4xx, 5xx) provenientes del servidor.
            LoadResult.Error(httpException)
        } catch (exception: Exception) {
            // Cualquier otra excepción inesperada.
            LoadResult.Error(exception)
        }
    }

    private fun resolveNextKey(
        response: PolicyPagedResponse,
        currentPage: Int,
        currentSize: Int
    ): Int? {
        // Si el backend envía el link "next", obtenemos el número de página directamente desde la URL.
        parsePageFromLink(response.next)?.let { return it }

        // Como respaldo, verificamos si aún hay elementos para cargar.
        if (currentSize == 0) return null

        return currentPage + 1
    }

    private fun resolvePreviousKey(response: PolicyPagedResponse, currentPage: Int): Int? {
        // Intentamos leer la página previa directamente desde el enlace "previous".
        parsePageFromLink(response.previous)?.let { return it }

        // Si no existe enlace previo, significa que estamos en la primera página.
        return if (currentPage == FIRST_PAGE) null else currentPage - 1
    }

    private fun parsePageFromLink(link: String?): Int? {
        if (link.isNullOrBlank()) return null
        return runCatching {
            Uri.parse(link).getQueryParameter(Constants.P_PAGE)?.toInt()
        }.getOrNull()
    }

    /**
     * Informa a la capa superior sobre el estado de la página actual (totales y enlaces).
     */
    private fun notifyMetadata(
        response: PolicyPagedResponse,
        currentPage: Int
    ) {
        val totalCount = response.count ?: 0
        val totalPages = calculateTotalPages(totalCount)
        val hasNext = !response.next.isNullOrBlank()
        val hasPrevious = !response.previous.isNullOrBlank() || currentPage > FIRST_PAGE

        onPageMetadata(
            PoliciesPageMetadata(
                currentPage = currentPage,
                totalPages = totalPages,
                totalCount = totalCount,
                hasPrevious = hasPrevious,
                hasNext = hasNext
            )
        )
    }

    private fun calculateTotalPages(totalCount: Int): Int {
        if (pageSize <= 0) return FIRST_PAGE
        return max(FIRST_PAGE, (totalCount + pageSize - 1) / pageSize)
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}
