package com.example.gitspy.models.issues

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issues")
data class Issue(
    var author_association: String?="",
    var comments: Int,
    var created_at: String,
    var html_url: String,
    @PrimaryKey
    var id: Int,
    var number: Int,
    var repository_url: String,
    var state: String,
    var title: String?="",
    var updated_at: String,
    var url: String,
    @Embedded(prefix = "IssuesUser_")
    var user: IssuesUser,
    var repoId:Long=0,
    var repoName : String = ""
)