package com.scottwehby.fetchexercise.ui.vm

import com.scottwehby.fetchexercise.data.model.Item

sealed interface UiState {
    data object NoNetwork : UiState
    object Loading : UiState
    data class Success(val groupedItems: Map<Int, List<Item>>) : UiState
    data class Error(val exception: Throwable) : UiState
}
