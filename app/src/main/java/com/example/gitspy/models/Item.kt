package com.example.gitspy.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class Item(
    @PrimaryKey
    var id: Int,
    var commits_url: String,
    var contributors_url: String,
    var created_at: String,
    var description: String,
    var forks_count: Int,
    var full_name: String,
    var git_commits_url: String,
    var html_url: String,
    var issue_comment_url: String,
    var issue_events_url: String,
    var issues_url: String,
    var language: String,
    var merges_url: String,
    var name: String,
    var open_issues: Int,
    var open_issues_count: Int,
    @Embedded
    var owner: Owner,
    var pulls_url: String,
    var pushed_at: String,
    var releases_url: String,
    var stargazers_count: Int,
    var updated_at: String,
    var url: String,
    var watchers_count: Int,

)