package com.example.gitspy.models.issues

import androidx.room.ColumnInfo

data class IssuesUser(
    val avatar_url: String,
    @ColumnInfo(name = "issueUserEventsUrl")
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    @ColumnInfo(name = "issueUserHtmlUrl")
    val html_url: String,
    @ColumnInfo(name = "issueUserId")
    val id: Int,
    val login: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    @ColumnInfo(name = "issueUserUrl")
    val url: String
)