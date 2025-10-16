package com.cursosant.insurance.policiesModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.policiesModule.model.PoliciesPageMetadata
import com.cursosant.insurance.policiesModule.model.PoliciesPagingSource
import com.cursosant.insurance.policiesModule.model.PoliciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
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
class PoliciesViewModel @Inject constructor(private val repository: PoliciesRepository) : BaseViewModel() {
    private val _policies = MutableLiveData<PagingData<Policy>>()
    val policies: LiveData<PagingData<Policy>> = _policies

    private val _isPoliciesEmpty = MutableLiveData(false)
    val isPoliciesEmpty: LiveData<Boolean> = _isPoliciesEmpty

    private val _paginationState = MutableLiveData(PaginationUiState())
    val paginationState: LiveData<PaginationUiState> = _paginationState

    private var fetchJob: Job? = null
    private var authToken: String? = null

    /**
     * Inicia o reinicia la carga paginada de pólizas fijando la página actual en [page].
     * El resultado se expone como [LiveData] para que el fragmento lo observe y envíe
     * cada página al adapter mediante `adapter.submitData(lifecycle, data)`.
     */
    fun getPolicies(token: String, page: Int = PoliciesPagingSource.FIRST_PAGE) {
        authToken = token
        loadPage(page)
    }

    fun setPoliciesEmpty(isEmpty: Boolean) {
        _isPoliciesEmpty.postValue(isEmpty)
    }

    fun updateLoading(isLoading: Boolean) {
        setProgress(isLoading)
    }

    fun notifyPoliciesError() {
        showMsg(R.string.policies_error)
    }

    /**
     * Solicita la página siguiente cuando el backend indica que existe un enlace "next".
     */
    fun goToNextPage() {
        val state = _paginationState.value ?: return
        if (state.hasNext) {
            loadPage(state.currentPage + 1)
        }
    }

    /**
     * Recupera la página anterior siempre que no estemos en la primera.
     */
    fun goToPreviousPage() {
        val state = _paginationState.value ?: return
        if (state.hasPrevious) {
            loadPage((state.currentPage - 1).coerceAtLeast(PoliciesPagingSource.FIRST_PAGE))
        }
    }

    /**
     * Ejecuta la llamada Retrofit por medio del repositorio forzando que Paging solo entregue
     * los registros de la página solicitada (sin scroll infinito).
     */
    private fun loadPage(page: Int) {
        val token = authToken ?: return
        fetchJob?.cancel()
        _isPoliciesEmpty.postValue(false)
        fetchJob = viewModelScope.launch {
            repository.getPolicies(
                token = token,
                initialPage = page,
                manualNavigation = true,
                onPageMetadata = ::onPageMetadata
            )
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _policies.postValue(pagingData)
                }
        }
    }

    /**
     * Transforma los metadatos de la respuesta en un estado observable para la UI.
     */
    private fun onPageMetadata(metadata: PoliciesPageMetadata) {
        val currentPage = metadata.currentPage.coerceAtLeast(PoliciesPagingSource.FIRST_PAGE)
        val totalPages = metadata.totalPages.coerceAtLeast(PoliciesPagingSource.FIRST_PAGE)

        _paginationState.postValue(
            PaginationUiState(
                currentPage = currentPage,
                totalPages = totalPages,
                totalCount = metadata.totalCount,
                hasPrevious = currentPage > PoliciesPagingSource.FIRST_PAGE,
                hasNext = metadata.hasNext
            )
        )
    }

    /**
     * Estado observable de la paginación visual.
     */
    data class PaginationUiState(
        val currentPage: Int = PoliciesPagingSource.FIRST_PAGE,
        val totalPages: Int = PoliciesPagingSource.FIRST_PAGE,
        val totalCount: Int = 0,
        val hasPrevious: Boolean = false,
        val hasNext: Boolean = false
    )
}