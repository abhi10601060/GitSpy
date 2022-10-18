package com.example.gitspy.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.gitspy.R
import com.example.gitspy.ui.fragments.CommitsFragment
import com.example.gitspy.ui.fragments.IssueFragment
import com.example.gitspy.ui.fragments.PullRequestsFragment
import com.example.gitspy.ui.fragments.RelasesFragment

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        handleFragments()

    }

    private fun handleFragments() {
        val intent = intent
        val path = intent.getStringExtra("path")

        val fragmentManager = supportFragmentManager

        when(path){
            "issues" ->{
                fragmentManager.beginTransaction().replace(R.id.parentFL , IssueFragment()).commit()
            }
            "commits" ->{
                fragmentManager.beginTransaction().replace(R.id.parentFL , CommitsFragment()).commit()
            }
            "releases" ->{
                fragmentManager.beginTransaction().replace(R.id.parentFL , RelasesFragment()).commit()
            }
            "prs" ->{
                fragmentManager.beginTransaction().replace(R.id.parentFL , PullRequestsFragment()).commit()
            }
        }
    }
}