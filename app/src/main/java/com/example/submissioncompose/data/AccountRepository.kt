package com.example.submissioncompose.data

import com.example.submissioncompose.model.Account2
import com.example.submissioncompose.model.AccountsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AccountRepository {

    fun getAccountBookmark(): Flow<List<Account2>> {
        return flowOf(AccountsData.accountBookmarkLocal)
    }

    fun getAccountBookmarkById(Id: String): Account2 {
        return AccountsData.accountBookmarkLocal.first {
            it.id == Id
        }
    }

    fun addAccountBookmark(account: Account2): Boolean {
        var isBookmarked = false;
        val myAccount = AccountsData.accountBookmarkLocal.filter {
            it.id.contains(account.id, ignoreCase = true)
        }
        if (myAccount.isEmpty()) {
            AccountsData.accountBookmarkLocal.add(account)
            isBookmarked = true;
        } else {
            AccountsData.accountBookmarkLocal.removeAll(myAccount)
        }
        println("Masuk add Account  ${AccountsData.accountBookmarkLocal.size} dan my account ${myAccount.isNotEmpty()}" )
        return isBookmarked;
    }

    fun getHeroes(): List<Account2> {
        return AccountsData.accountsLocal
    }

    fun searchHeroes(query: String): List<Account2>{
        return AccountsData.accountsLocal.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getAccountById(rewardId: String): Account2 {
        return getHeroes().first {
            it.id == rewardId
        }
    }

    companion object {
        @Volatile
        private var instance: AccountRepository? = null

        fun getInstance(): AccountRepository =
            instance ?: synchronized(this) {
                AccountRepository().apply {
                    instance = this
                }
            }
    }
}