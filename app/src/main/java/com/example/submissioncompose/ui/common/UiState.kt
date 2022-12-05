package com.example.submissioncompose.ui.common

sealed class UiState<out T: Any?> {

    object Loading : UiState<Nothing>()

    data class Success<out T: Any>(val data: T) : UiState<T>()

    data class Error(val errorMessage: String) : UiState<Nothing>()
}

sealed class UiStateBookmarked<out T: Any?> {
    object NothingHappen : UiStateBookmarked<Nothing>()

    data class SuccessBookmarked<out T: Any>(val dataBookmarked: T) : UiStateBookmarked<T>()

    data class Success<out T: Any>(val data: T) : UiStateBookmarked<T>()

}