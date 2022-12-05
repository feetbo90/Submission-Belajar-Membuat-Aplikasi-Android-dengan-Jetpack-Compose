package com.example.submissioncompose.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissioncompose.data.AccountRepository
import com.example.submissioncompose.ui.screen.bookmark.BookmarkViewModel
import com.example.submissioncompose.ui.screen.detail.DetailScreenViewModel
import com.example.submissioncompose.ui.screen.list_account.ListAccountViewModel

class ViewModelFactory(private val repository: AccountRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListAccountViewModel::class.java)) {
            return ListAccountViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            return DetailScreenViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}