package com.example.gigmarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigmarket.data.model.Provider
import com.example.gigmarket.data.repository.ProviderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProviderDetailState {
    object Loading : ProviderDetailState()
    data class Success(val provider: Provider) : ProviderDetailState()
    data class Error(val message: String) : ProviderDetailState()
}

class ProviderDetailViewModel : ViewModel() {

    private val repository = ProviderRepository()

    private val _uiState = MutableStateFlow<ProviderDetailState>(ProviderDetailState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchProvider(id: String) {
        viewModelScope.launch {
            _uiState.value = ProviderDetailState.Loading
            val result = repository.getProviderById(id)
            result.onSuccess { provider ->
                _uiState.value = ProviderDetailState.Success(provider)
            }
            result.onFailure {
                _uiState.value = ProviderDetailState.Error(it.message ?: "Failed to fetch provider details")
            }
        }
    }
}
