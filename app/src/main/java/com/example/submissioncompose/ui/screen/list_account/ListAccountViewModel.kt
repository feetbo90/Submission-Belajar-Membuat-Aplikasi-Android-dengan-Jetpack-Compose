package com.example.submissioncompose.ui.screen.list_account

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.submissioncompose.data.AccountRepository
import com.example.submissioncompose.model.Account2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListAccountViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _groupedAccounts = MutableStateFlow(
        repository.getHeroes()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedAccounts: StateFlow<Map<Char, List<Account2>>> get() = _groupedAccounts

    private val _query = mutableStateOf("")

    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedAccounts.value = repository.searchHeroes(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}
