package com.example.gitspy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gitspy.models.AccessToken
import com.example.gitspy.models.Item
import com.example.gitspy.models.commits.CommitListItem
import com.example.gitspy.models.issues.Issue
import com.example.gitspy.models.issues_events.IssueEventsItem
import com.example.gitspy.models.pulls.PullRequestsItem
import com.example.gitspy.models.releases.ReleaseItem

@Dao
interface TrackRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun trackRepo(item: Item) :Long

    @Query("Select * from repositories")
    fun getAllTrackedRepos() : LiveData<List<Item>>

    @Delete
    suspend fun deleteRepo(item: Item)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addIssue(issue: Issue):Long

    @Query("delete from issues where repoId = :repoId")
    suspend fun deleteIssues(repoId : Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCommit(commitListItem: CommitListItem) : Long

    @Query("delete from commits where repoId = :repoId")
    suspend fun deleteCommits(repoId : Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRelease(release : ReleaseItem):Long

    @Query("delete from releases where repoId = :repoId")
    suspend fun deleteReleases(repoId : Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPullRequest(pr : PullRequestsItem):Long

    @Query("delete from pull_requests where repoId = :repoId")
    suspend fun deletePullRequests(repoId : Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addIssueEvent(issueEventsItem: IssueEventsItem):Long

    @Query("delete from issue_events where repoId= :repoId")
    suspend fun deleteIssueEvents(repoId : Long)

    @Query("UPDATE repositories set issue_events_count = issue_events_count + 1 where id = :repoId ")
    suspend fun incrementIssueEventCounts(repoId : Long)

    @Query("UPDATE repositories set pull_requests_count = pull_requests_count + 1 where id = :repoId ")
    suspend fun incrementPullRequestsCount(repoId : Long)

    @Query("UPDATE repositories set commits_count = commits_count + 1 where id = :repoId ")
    suspend fun incrementCommitsCount(repoId : Long)

    @Query("UPDATE repositories set releases_count = releases_count + 1 where id = :repoId ")
    suspend fun incrementReleasesCount(repoId : Long)

    @Query("UPDATE repositories set unseen_issue_events_count = unseen_issue_events_count + 1 where id = :repoId ")
    suspend fun incrementUnseenIssueEventCounts(repoId : Long)

    @Query("UPDATE repositories set unseen_pull_requests_count = unseen_pull_requests_count + 1 where id = :repoId ")
    suspend fun incrementUnseenPullRequestsCount(repoId : Long)

    @Query("UPDATE repositories set unseen_commits_count = unseen_commits_count + 1 where id = :repoId ")
    suspend fun incrementUnseenCommitsCount(repoId : Long)

    @Query("UPDATE repositories set unseen_releases_count = unseen_releases_count + 1 where id = :repoId ")
    suspend fun incrementUnseenReleasesCount(repoId : Long)

    @Query("Select * from repositories")
    suspend fun getAllTrackedReposList() : List<Item>

//********************************************************* Stats Repository *************************************************************

    @Query("select * from issues where repoId = :id ")
    fun getSavedIssues(id : Long) : LiveData<List<Issue>>


    @Query("select * from pull_requests where repoId = :id ")
    fun getSavedPrs(id : Long) : LiveData<List<PullRequestsItem>>

//********************************************************* Authorization service *************************************************************

    @Insert
    fun saveToken(token : AccessToken) : Long

    @Query("select * from token")
    fun getTokenFromDB() : List<AccessToken>

}