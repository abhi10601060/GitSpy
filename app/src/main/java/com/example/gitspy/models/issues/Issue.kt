package com.example.gitspy.models.issues

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issues")
data class Issue(
    var author_association: String,
    var comments: Int,
    var comments_url: String,
    var created_at: String,
    var events_url: String,
    var html_url: String,
    @PrimaryKey
    var id: Int,
    var locked: Boolean,
    var node_id: String,
    var number: Int,
    var repository_url: String,
    var state: String,
    var title: String,
    var updated_at: String,
    var url: String,
    @Embedded
    var user: IssuesUser,
    var repoId:Int
)