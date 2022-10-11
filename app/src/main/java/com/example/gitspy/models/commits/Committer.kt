package com.example.gitspy.models.commits

data class Committer(
    val date: String,
    val email: String? = "",
    val name: String
)