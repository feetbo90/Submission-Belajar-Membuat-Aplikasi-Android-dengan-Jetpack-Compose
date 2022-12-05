package com.example.submissioncompose.model

data class Account(
    val id: String,
    val name: String,
    val photoUrl: String
)

data class Account2(
    val id: String,
    val name: String,
    val sex: String,
    val age: Int,
    val description: String,
    val email: String,
    val accountImageId: Int = 0
)