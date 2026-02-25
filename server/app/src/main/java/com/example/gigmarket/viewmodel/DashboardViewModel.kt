package com.example.gigmarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigmarket.data.model.Provider
import com.example.gigmarket.data.repository.ProviderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.`asStateFlow`
import kotlinx.coroutines.launch

sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val providers: List<Provider>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}

class DashboardViewModel : ViewModel() {

    private val repository = ProviderRepository()

    private val _uiState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(listOf("All", "Repairs", "Cleaning", "Plumbing", "Electrical"))
    val categories = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    init {
        fetchProviders()
    }

    fun fetchProviders(category: String? = null) {
        viewModelScope.launch {
            _uiState.value = DashboardState.Loading
            
            // Hardcoded coordinates for now (Bangalore)
            val result = repository.getNearbyProviders(
                lat = 12.9716, 
                lng = 77.5946, 
                category = if (category == "All") null else category
            )

            result.onSuccess { providers ->
                _uiState.value = DashboardState.Success(providers)
            }

            result.onFailure {
                _uiState.value = DashboardState.Error(it.message ?: "Failed to fetch providers")
            }
        }
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        fetchProviders(category)
    }
}
