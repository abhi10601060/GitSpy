package com.example.gitspy.models.issues_events

data class Issue(
    val created_at: String,
    val html_url: String,
    val id: Int,
    val number: Int,
    val state: String,
    val title: String,
    val updated_at: String,
)