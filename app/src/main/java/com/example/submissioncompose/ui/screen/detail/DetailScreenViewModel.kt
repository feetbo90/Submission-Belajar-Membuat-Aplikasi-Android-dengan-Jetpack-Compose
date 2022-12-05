package com.example.submissioncompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.AccountRepository
import com.example.submissioncompose.model.Account2
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.common.UiStateBookmarked
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val repository: AccountRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Account2>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Account2>>
        get() = _uiState

    private val _uiStateBookmarked: MutableStateFlow<UiStateBookmarked<Boolean>> =
        MutableStateFlow(UiStateBookmarked.NothingHappen)

    val uiStateBookmarked: StateFlow<UiStateBookmarked<Boolean>>
        get() = _uiStateBookmarked

    fun getAccountById(rewardId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAccountById(rewardId))
        }
    }

    fun addAccount(account: Account2) {
        viewModelScope.launch {
            _uiStateBookmarked.value = UiStateBookmarked.NothingHappen
            _uiStateBookmarked.value = UiStateBookmarked.SuccessBookmarked(repository.addAccountBookmark(account))
        }
    }

}