package com.cursosant.insurance.policiesModule.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.PoliciesPage
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.policiesModule.model.PoliciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policiesModule.viewModel
 * Created by Alain Nicolás Tello on 02/06/23 at 9:52
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
@HiltViewModel
class PoliciesViewModel @Inject constructor(private val repository: PoliciesRepository) : BaseViewModel() {
    private val _policies = MutableLiveData<List<Policy>>()
    val policies: LiveData<List<Policy>> = _policies

    private val _canGoNext = MutableLiveData(false)
    val canGoNext: LiveData<Boolean> = _canGoNext

    private val _canGoPrevious = MutableLiveData(false)
    val canGoPrevious: LiveData<Boolean> = _canGoPrevious

    private val _pageIndicator = MutableLiveData("")
    val pageIndicator: LiveData<String> = _pageIndicator

    private val _isPaginationVisible = MutableLiveData(false)
    val isPaginationVisible: LiveData<Boolean> = _isPaginationVisible

    private var authToken: String? = null
    private var currentPage: Int = 1
    private var nextPage: Int? = null
    private var previousPage: Int? = null
    private var totalCount: Int? = null
    private var pageSize: Int? = null

    fun loadFirstPage(token: String) {
        authToken = token
        resetPagination()
        requestPage(1)
    }

    fun loadNextPage() {
        val targetPage = nextPage ?: return
        requestPage(targetPage)
    }

    fun loadPreviousPage() {
        val targetPage = previousPage ?: return
        requestPage(targetPage)
    }

    private fun requestPage(page: Int) {
        val token = authToken ?: return
        val previousNextState = _canGoNext.value
        val previousPreviousState = _canGoPrevious.value
        _canGoNext.postValue(false)
        _canGoPrevious.postValue(false)
        executeAction {
            try {
                val result = repository.getPolicies(token, page)
                updatePolicies(result.policies)
                updatePaginationState(result, page)
            } catch (exception: InsuranceException) {
                _canGoNext.postValue(previousNextState ?: false)
                _canGoPrevious.postValue(previousPreviousState ?: false)
                throw exception
            }
        }
    }

    private fun resetPagination() {
        currentPage = 1
        nextPage = null
        previousPage = null
        totalCount = null
        pageSize = null
        _canGoNext.postValue(false)
        _canGoPrevious.postValue(false)
        _pageIndicator.postValue("")
        _isPaginationVisible.postValue(false)
    }

    private fun updatePolicies(policies: List<Policy>) {
        _policies.postValue(policies)
    }

    private fun updatePaginationState(page: PoliciesPage, requestedPage: Int) {
        currentPage = requestedPage
        totalCount = page.count
        if (pageSize == null && page.policies.isNotEmpty()) {
            pageSize = page.policies.size
        }

        nextPage = extractPageNumber(page.next)
        previousPage = extractPageNumber(page.previous)

        _canGoNext.postValue(nextPage != null)
        _canGoPrevious.postValue(previousPage != null)

        val hasPolicies = page.policies.isNotEmpty()
        val hasCount = hasTotalCount()
        val hasNavigation = nextPage != null || previousPage != null
        val shouldShowIndicator = hasPolicies || hasCount || hasNavigation
        _pageIndicator.postValue(if (shouldShowIndicator) buildPageIndicator() else "")
        _isPaginationVisible.postValue(shouldShowIndicator)
    }

    private fun hasTotalCount(): Boolean = (totalCount ?: 0) > 0

    private fun buildPageIndicator(): String = buildString {
        append("Página $currentPage")
        determineTotalPages()?.let { totalPages ->
            append(" de $totalPages")
        }
        totalCount?.takeIf { it > 0 }?.let { total ->
            append(" · $total pólizas")
        }
    }

    private fun determineTotalPages(): Int? {
        val count = totalCount ?: return null
        if (count <= 0) return null
        val size = pageSize ?: return null
        if (size <= 0) return null
        val pages = (count + size - 1) / size
        return if (pages > 0) pages else 1
    }

    private fun extractPageNumber(pageUrl: String?): Int? {
        if (pageUrl.isNullOrBlank()) return null
        return try {
            Uri.parse(pageUrl).getQueryParameter("page")?.toIntOrNull()
        } catch (_: Exception) {
            null
        }
    }
}
