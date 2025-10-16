package com.cursosant.insurance.policiesModule.model

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.utils.Constants
import kotlinx.coroutines.CancellationException

class PoliciesPagingSource(
    private val dataSource: DataSource,
    private val token: String,
    private val username: String,
    private val pageSize: Int
) : PagingSource<Int, Policy>() {

    override fun getRefreshKey(state: PagingState<Int, Policy>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Policy> {
        val page = params.key ?: FIRST_PAGE
        val loadSize = params.loadSize.takeIf { it > 0 } ?: pageSize
        return try {
            val response = dataSource.getPolicies(token, username, page, loadSize)
            val policies = response.items

            val nextKey = resolveNextKey(response, page, policies.size, loadSize)
            val prevKey = resolvePreviousKey(response, page)

            LoadResult.Page(
                data = policies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            if (exception is CancellationException) throw exception
            LoadResult.Error(exception)
        }
    }

    private fun resolveNextKey(
        response: PolicyPagedResponse,
        currentPage: Int,
        currentSize: Int,
        loadSize: Int
    ): Int? {
        parsePageFromLink(response.next)?.let { return it }

        response.totalCount.let { total ->
            val effectivePageSize = if (loadSize > 0) loadSize else pageSize
            val totalPages = if (effectivePageSize == 0) 0 else (total + effectivePageSize - 1) / effectivePageSize
            if (totalPages != 0 && currentPage >= totalPages) {
                return null
            }
        }

        return if (currentSize == 0) null else currentPage + 1
    }

    private fun resolvePreviousKey(response: PolicyPagedResponse, currentPage: Int): Int? {
        parsePageFromLink(response.previous)?.let { return it }
        return if (currentPage == FIRST_PAGE) null else currentPage - 1
    }

    private fun parsePageFromLink(link: String?): Int? {
        if (link.isNullOrBlank()) return null
        return runCatching {
            Uri.parse(link).getQueryParameter(Constants.P_PAGE)?.toInt()
        }.getOrNull()
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}
