package com.example.gitspy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gitspy.models.Item
import com.example.gitspy.models.commits.CommitListItem
import com.example.gitspy.models.issues.Issue

@Dao
interface TrackRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun trackRepo(item: Item)

    @Query("Select * from repositories")
    fun getAllTrackedRepos() : LiveData<List<Item>>

    @Delete
    suspend fun deleteRepo(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIssue(issue: Issue)

    @Query("delete from issues where repoId = :repoId")
    suspend fun deleteIssues(repoId : Long)

    @Insert
    suspend fun addCommit(commitListItem: CommitListItem)
}