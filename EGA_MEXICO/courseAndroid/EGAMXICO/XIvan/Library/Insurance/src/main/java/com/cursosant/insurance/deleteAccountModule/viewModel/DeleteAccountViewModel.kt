package com.cursosant.insurance.deleteAccountModule.viewModel

import androidx.lifecycle.viewModelScope
import com.cursosant.insurance.common.entities.BaseResponse
import com.cursosant.insurance.common.viewModel.BaseViewModel
import com.cursosant.insurance.deleteAccountModule.model.DeleteAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val repository: DeleteAccountRepository
) : BaseViewModel() {

    // Alias para compatibilidad si el Fragment llama deleteAccount(...)
    fun deleteAccount(username: String, password: String?) {
        disableAccount(username, password)
    }

    // Versión simple (sin callback)
    fun disableAccount(username: String, password: String?) {
        viewModelScope.launch {
            try {
                repository.disableAccount(username, password)
            } catch (_: Exception) {
                // Manejo de error si necesitas propagarlo por otro LiveData
            }
        }
    }

    // Overload con callback para trailing lambda desde el Fragment
    fun disableAccount(
        username: String,
        password: String?,
        onResult: (BaseResponse) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val res = repository.disableAccount(username, password)
                onResult(res)
            } catch (_: Exception) {
                // Manejo de error opcional
            }
        }
    }
}
