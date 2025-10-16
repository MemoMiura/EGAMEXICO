package com.cursosant.insurance.policiesModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingData.Companion.empty
import androidx.paging.PagingData.Companion.from
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.InsuranceException
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.policiesModule.model.PoliciesPagingSource
import com.cursosant.insurance.policiesModule.model.PoliciesRepository
import com.cursosant.insurance.policiesModule.model.PolicyPagedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
class PoliciesViewModel @Inject constructor(
    private val repository: PoliciesRepository
) : BaseViewModel() {

    private val _policies = MutableLiveData<PagingData<Policy>>(empty())
    val policies: LiveData<PagingData<Policy>> = _policies

    private val _isPoliciesEmpty = MutableLiveData(false)
    val isPoliciesEmpty: LiveData<Boolean> = _isPoliciesEmpty

    private val _isNextEnabled = MutableLiveData(false)
    val isNextEnabled: LiveData<Boolean> = _isNextEnabled

    private val _isPreviousEnabled = MutableLiveData(false)
    val isPreviousEnabled: LiveData<Boolean> = _isPreviousEnabled

    private val _pageIndicator = MutableLiveData<String>()
    val pageIndicator: LiveData<String> = _pageIndicator

    private val _emptyStateMessage = MutableLiveData(R.string.policies_empty_msg)
    val emptyStateMessage: LiveData<Int> = _emptyStateMessage

    private var sessionToken: String = ""
    private var sessionUsername: String = ""
    private var currentPage: Int = PoliciesPagingSource.FIRST_PAGE
    private var totalPages: Int = 0
    private var hasLoadedAnyPage = false
    private var isLoadingPage = false
    private var loadJob: Job? = null

    fun onSessionAvailable(token: String?, username: String?) {
        val normalizedToken = token?.trim().orEmpty()
        val normalizedUsername = username?.trim().orEmpty()

        if (normalizedToken.isEmpty() || normalizedUsername.isEmpty()) {
            clearPolicies()
            return
        }

        val hasSessionChanged =
            normalizedToken != sessionToken || normalizedUsername != sessionUsername

        sessionToken = normalizedToken
        sessionUsername = normalizedUsername

        if (hasSessionChanged) {
            resetPagination()
        }
    }

    fun refreshCurrentPage() {
        if (sessionToken.isEmpty() || sessionUsername.isEmpty()) {
            clearPolicies()
            return
        }

        loadPage(currentPage)
    }

    fun loadNextPage() {
        if (_isNextEnabled.value != true) return
        val targetPage = if (totalPages == 0) currentPage + 1 else minOf(currentPage + 1, totalPages)
        loadPage(targetPage)
    }

    fun loadPreviousPage() {
        if (_isPreviousEnabled.value != true) return
        val targetPage = maxOf(currentPage - 1, PoliciesPagingSource.FIRST_PAGE)
        loadPage(targetPage)
    }

    fun onNextPageClicked() = loadNextPage()

    fun onPreviousPageClicked() = loadPreviousPage()

    fun setPoliciesEmpty(isEmpty: Boolean) {
        _isPoliciesEmpty.postValue(isEmpty)
    }

    fun updateLoading(isLoading: Boolean) {
        setProgress(isLoading)
    }

    fun notifyPoliciesError() {
        showMsg(R.string.policies_error)
    }

    private fun loadPage(requestedPage: Int) {
        if (isLoadingPage) return
        val safePage = maxOf(requestedPage, PoliciesPagingSource.FIRST_PAGE)

        isLoadingPage = true
        updateLoading(true)
        _isPoliciesEmpty.postValue(false)
        _emptyStateMessage.postValue(R.string.policies_empty_msg)

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            try {
                val response = repository.getPoliciesPage(
                    token = sessionToken,
                    username = sessionUsername,
                    page = safePage
                )

                handlePoliciesResponse(response, safePage)
            } catch (exception: InsuranceException) {
                handlePoliciesError(exception)
            } catch (exception: Exception) {
                handlePoliciesError(null)
            } finally {
                isLoadingPage = false
                updateLoading(false)
            }
        }
    }

    private fun handlePoliciesResponse(response: PolicyPagedResponse, page: Int) {
        val policies = response.items
        val pagingData = if (policies.isEmpty()) empty<Policy>() else from(policies)

        _policies.postValue(pagingData)

        val isEmpty = policies.isEmpty()
        _isPoliciesEmpty.postValue(isEmpty)

        val totalCount = response.totalCount
        totalPages = calculateTotalPages(totalCount)
        currentPage = minOf(page, if (totalPages == 0) page else totalPages)
        hasLoadedAnyPage = true

        updatePaginationControls(response, policies.size)
    }

    private fun calculateTotalPages(totalCount: Int): Int {
        if (totalCount <= 0) return 0
        return (totalCount + PAGE_SIZE - 1) / PAGE_SIZE
    }

    private fun updatePaginationControls(response: PolicyPagedResponse, currentSize: Int) {
        val hasNext = hasNextPage(response, currentSize)
        val hasPrevious = hasPreviousPage(response)

        _isNextEnabled.postValue(hasNext)
        _isPreviousEnabled.postValue(hasPrevious)

        val indicator = buildPageIndicator()
        _pageIndicator.postValue(indicator)
    }

    private fun hasNextPage(response: PolicyPagedResponse, currentSize: Int): Boolean {
        if (!response.next.isNullOrBlank()) return true
        if (totalPages == 0) return currentSize >= PAGE_SIZE
        return currentPage < totalPages
    }

    private fun hasPreviousPage(response: PolicyPagedResponse): Boolean {
        if (!response.previous.isNullOrBlank()) return true
        return currentPage > PoliciesPagingSource.FIRST_PAGE
    }

    private fun buildPageIndicator(): String {
        val safeTotalPages = if (totalPages == 0 && hasLoadedAnyPage) 1 else totalPages
        val current = if (hasLoadedAnyPage) currentPage else PoliciesPagingSource.FIRST_PAGE
        return "$current / ${if (safeTotalPages == 0) 1 else safeTotalPages}"
    }

    private fun handlePoliciesError(exception: InsuranceException?) {
        exception?.let { showMsg(it.typeError.resMsg) } ?: showMsg(R.string.policies_error)
        if (!hasLoadedAnyPage) {
            _isPoliciesEmpty.postValue(true)
            _emptyStateMessage.postValue(R.string.policies_error)
        }
        _isNextEnabled.postValue(false)
        _isPreviousEnabled.postValue(currentPage > PoliciesPagingSource.FIRST_PAGE)
    }

    private fun resetPagination() {
        loadJob?.cancel()
        loadJob = null
        isLoadingPage = false
        currentPage = PoliciesPagingSource.FIRST_PAGE
        totalPages = 0
        hasLoadedAnyPage = false
        _policies.postValue(empty())
        _isPoliciesEmpty.postValue(false)
        _isNextEnabled.postValue(false)
        _isPreviousEnabled.postValue(false)
        _pageIndicator.postValue(null)
        _emptyStateMessage.postValue(R.string.policies_empty_msg)
    }

    private fun clearPolicies() {
        sessionToken = ""
        sessionUsername = ""
        resetPagination()
        updateLoading(false)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
