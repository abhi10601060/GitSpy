package com.example.gitspy.models.pulls

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pull_requests")
data class PullRequestsItem(

    val body: String?="",
    val closed_at: String?="",
    val created_at: String?="",
    val html_url: String?="",
    @PrimaryKey
    val id: Int,
    val merged_at: String?="",
    val number: Int,
    val state: String?="",
    val title: String?="",
    val updated_at: String?="",
    var repoId :Long =0
)