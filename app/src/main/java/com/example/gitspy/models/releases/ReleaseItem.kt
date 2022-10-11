package com.example.gitspy.models.releases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "releases")
data class ReleaseItem(
    var body: String?="",
    val created_at: String,
    val html_url: String,
    @PrimaryKey
    val id: Int,
    var name: String?="",
    val published_at: String,
    var repoId : Long = 0
)