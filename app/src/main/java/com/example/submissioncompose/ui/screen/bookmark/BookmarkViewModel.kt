package com.example.submissioncompose.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.AccountRepository
import com.example.submissioncompose.model.Account2
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Account2>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Account2>>>
        get() = _uiState

    fun getAllBookmarked() {
        viewModelScope.launch {
            repository.getAccountBookmark()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { bookMarked ->
                    _uiState.value = UiState.Success(bookMarked)
                }
        }
    }
}