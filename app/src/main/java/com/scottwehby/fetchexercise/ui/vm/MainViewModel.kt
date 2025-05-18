package com.scottwehby.fetchexercise.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.scottwehby.fetchexercise.data.model.Item
import com.scottwehby.fetchexercise.data.repository.ItemRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()
    private val _intents = Channel<UiIntent>(Channel.CONFLATED)

    init {
        viewModelScope.launch {
            intentProcessor()
        }
    }

    suspend fun sendIntent(intent: UiIntent) {
        _intents.send(intent)
    }

    private suspend fun intentProcessor() {
        for (intent in _intents) {
            when (intent) {
                UiIntent.Retry,
                UiIntent.ScreenOpened -> fetchItems()
            }
        }
    }

    private suspend fun fetchItems() {
        itemRepository.getGroupedItems()
            .onSuccess { items ->
                _state.value = UiState.Success(items)
            }
            .onFailure { exception ->
                _state.value = UiState.Error(exception)
            }
    }
}

class MainViewModelFactory(
    private val repo: ItemRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            MainViewModel::class.java -> MainViewModel(repo) as T
            else -> throw IllegalArgumentException("Unknown VM: $modelClass")
        }
}
