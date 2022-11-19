package com.example.gitspy.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "token")
data class AccessToken(
    @SerializedName("access_token")
    @PrimaryKey
    val access_token : String = "",
    @SerializedName("token_type")
    val token_type  : String? =""
) {

}