package com.example.gitspy.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitspy.models.Item

@Dao
interface TrackRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun trackRepo(item: Item)

    @Query("Select * from repositories")
    fun getAllTrackedRepos() : LiveData<List<Item>>

}