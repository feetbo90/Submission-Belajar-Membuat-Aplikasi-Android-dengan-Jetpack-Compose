package com.example.submissioncompose.di

import com.example.submissioncompose.data.AccountRepository

object Injection {
    fun provideRepository(): AccountRepository {
        return AccountRepository.getInstance()
    }
}