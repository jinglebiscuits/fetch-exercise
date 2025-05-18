package com.scottwehby.fetchexercise.ui.vm

import com.scottwehby.fetchexercise.data.model.Group

sealed interface UiState {
    data object NoNetwork : UiState
    object Loading : UiState
    data class Success(val groupedItems: List<Group>) : UiState
    data class Error(val exception: Throwable) : UiState
}
