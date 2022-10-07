package com.example.gitspy.models

data class RepoList(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)