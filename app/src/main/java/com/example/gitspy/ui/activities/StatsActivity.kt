package com.example.gitspy.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.gitspy.R
import com.example.gitspy.ui.fragments.CommitsFragment
import com.example.gitspy.ui.fragments.IssueFragment
import com.example.gitspy.ui.fragments.PullRequestsFragment
import com.example.gitspy.ui.fragments.ReleasesFragment
import com.example.gitspy.utility.GitSpyApplication
import com.example.gitspy.viewmodels.StatsViewModel
import com.example.gitspy.viewmodels.StatsViewModelFactory

class StatsActivity : AppCompatActivity() {

    lateinit var viewModel : StatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val repository = (application as GitSpyApplication).statsRepository
        viewModel = ViewModelProvider(this , StatsViewModelFactory(repository)).get(StatsViewModel::class.java)

        handleFragments()

    }

    private fun handleFragments() {
        val intent = intent
        val bundle = intent.getBundleExtra("bundle")
        val path = bundle?.getString("path")
        val fragmentManager = supportFragmentManager

        when(path){
            "issues" ->{
                bundle.getLong("repoID").let { viewModel.getAllIssues(it) }
                val ownerName = bundle.getString("owner" , "default")
                val repoName = bundle.getString("repoName" , "default")
                Log.d("ABHI", "handleFragments: $ownerName , $repoName")
                viewModel.getOpenIssues(ownerName,repoName)
                viewModel.getClosedIssues(ownerName,repoName)

                val issueFragment = IssueFragment()
                issueFragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.parentFL ,issueFragment).commit()
            }
            "commits" ->{
                val ownerName = bundle.getString("owner" , "default")
                val repoName = bundle.getString("repoName" , "default")
                Log.d("ABHI", "handleFragments: $ownerName , $repoName")
                viewModel.getAllCommits(ownerName,repoName)

                val commitsFragment = CommitsFragment()
                commitsFragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.parentFL ,commitsFragment).commit()
            }
            "releases" ->{
                val ownerName = bundle.getString("owner" , "default")
                val repoName = bundle.getString("repoName" , "default")
                Log.d("ABHI", "handleFragments: $ownerName , $repoName")
                viewModel.getAllReleases(ownerName,repoName)

                val releasesFragment = ReleasesFragment()
                releasesFragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.parentFL , releasesFragment).commit()
            }
            "prs" ->{
                bundle.getLong("repoID").let { viewModel.getAllSavedPrNotifications(it) }
                val ownerName = bundle.getString("owner" , "default")
                val repoName = bundle.getString("repoName" , "default")
                Log.d("ABHI", "handleFragments: $ownerName , $repoName")
                viewModel.getOpenPullRequest(ownerName,repoName)
                viewModel.getClosedPullRequest(ownerName,repoName)

                val prsFragment = PullRequestsFragment()
                prsFragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.parentFL , prsFragment).commit()
            }
        }
    }
}