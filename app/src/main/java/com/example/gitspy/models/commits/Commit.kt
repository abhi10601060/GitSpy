package com.example.gitspy.models.commits

import androidx.room.Embedded

data class Commit(
    @Embedded(prefix = "commiter_")
    val committer: Committer,
    val message: String?="",
    val url: String,

)