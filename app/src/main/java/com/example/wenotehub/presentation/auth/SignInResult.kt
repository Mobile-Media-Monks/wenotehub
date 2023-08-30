package com.example.wenotehub.presentation.auth

data class SignInResult(
    val data: UserD?,
    val errorMessage: String?
)

data class UserD(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)