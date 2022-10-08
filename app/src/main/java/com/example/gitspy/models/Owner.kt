package com.example.gitspy.models

import androidx.room.ColumnInfo

data class Owner(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    @ColumnInfo(name = "owner_html_url")
    val html_url: String,
    @ColumnInfo(name = "owner_id")
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    @ColumnInfo(name = "owner_url")
    val url: String
)