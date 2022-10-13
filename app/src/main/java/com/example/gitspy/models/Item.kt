package com.example.gitspy.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class Item(
    @PrimaryKey
    var id: Long,
    var created_at: String="",
    var description: String?="",
    var forks_count: Int=0,
    var full_name: String="",
    var html_url: String="",
    var language: String?="",
    var name: String="",
    var open_issues_count: Int,
    @Embedded(prefix = "owner_")
    var owner: Owner,
    var pushed_at: String="",
    var stargazers_count: Int,
    var updated_at: String="",
    var url: String="",
    var watchers_count: Int=0,

    var issue_events_count : Int =0,
    var commits_count :Int = 0,
    var releases_count :Int =0,
    var pull_requests_count : Int =0,

    var unseen_issue_events_count : Int =0,
    var unseen_commits_count :Int = 0,
    var unseen_releases_count :Int =0,
    var unseen_pull_requests_count : Int =0


)