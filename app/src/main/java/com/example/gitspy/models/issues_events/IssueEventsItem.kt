package com.example.gitspy.models.issues_events

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issue_events")
data class IssueEventsItem(
    @Embedded(prefix = "actor_")
    val actor: Actor,
    val created_at: String,
    val event: String?="",
    @PrimaryKey
    val id: Long,
    @Embedded(prefix = "issue_")
    val issue: Issue,
    val url: String,
    var repoId : Long = 0,
    var repoName : String =""
)