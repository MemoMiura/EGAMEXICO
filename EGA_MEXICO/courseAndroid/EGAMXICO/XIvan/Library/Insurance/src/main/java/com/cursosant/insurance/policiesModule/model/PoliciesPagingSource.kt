package com.cursosant.insurance.policiesModule.model

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.utils.Constants

class PoliciesPagingSource(
    private val dataSource: DataSource,
    private val token: String,
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
        return try {
            val response = dataSource.getPolicies(token, page, pageSize)
            val policies = response.results.orEmpty()

            val nextKey = resolveNextKey(response, page, policies.size)
            val prevKey = resolvePreviousKey(response, page)

            LoadResult.Page(
                data = policies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    private fun resolveNextKey(
        response: PolicyPagedResponse,
        currentPage: Int,
        currentSize: Int
    ): Int? {
        parsePageFromLink(response.next)?.let { return it }

        response.count?.let { total ->
            val totalPages = if (pageSize == 0) 0 else (total + pageSize - 1) / pageSize
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
