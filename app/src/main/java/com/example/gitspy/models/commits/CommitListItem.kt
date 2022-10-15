package com.example.gitspy.models.commits

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commits")
data class CommitListItem(

    @PrimaryKey
    val sha : String,
    @Embedded(prefix = "commit_")
    val commit: Commit,
    val html_url: String,
    var repoId : Long = 0,
    var repoName : String =""
)