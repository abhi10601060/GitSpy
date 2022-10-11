package com.example.gitspy.models

import androidx.room.ColumnInfo

data class Owner(
    val avatar_url: String="",
    val html_url: String="",
    val id: Long=0,
    val login: String="",
    val type: String="",
    val url: String=""
)