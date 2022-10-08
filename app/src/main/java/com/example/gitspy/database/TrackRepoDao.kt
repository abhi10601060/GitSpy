package com.example.gitspy.database

import androidx.room.Dao
import androidx.room.Insert
import com.example.gitspy.models.Item

@Dao
interface TrackRepoDao {

    @Insert
    suspend fun trackRepo(item: Item)

}