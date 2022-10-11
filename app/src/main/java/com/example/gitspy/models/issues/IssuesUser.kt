package com.example.gitspy.models.issues

import androidx.room.ColumnInfo

data class IssuesUser(
    val avatar_url: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val type: String?="",
    val url: String
)