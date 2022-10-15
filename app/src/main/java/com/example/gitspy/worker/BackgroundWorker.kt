package com.example.gitspy.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.gitspy.utility.GitSpyApplication
import com.example.gitspy.utility.NetworkUtility
import kotlinx.coroutines.*

class BackgroundWorker : BroadcastReceiver() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context : Context?, p1: Intent?) {

        val repository = (context?.applicationContext as GitSpyApplication).repository

        Log.d("ABHI", "doWork: request sent from outside scope")

        if(NetworkUtility.isNetworkAvailable(context)){
            CoroutineScope(Dispatchers.IO).launch{
                Log.d("ABHI", "doWork: request sent from inside scope")
                val repos = repository.getTrackedRepoList()
                for(repo in repos){
                    Log.d("ABHI", "doWork: request sent for ${repo.full_name}")

                    val job = launch(Dispatchers.IO){
                        repository.addIssuesBackground(repo.owner.login , repo.name , repo.id)
                    }
                    job.join()
                    val job1 = launch(Dispatchers.IO){
                        repository.addIssueEventsBackground(repo.owner.login , repo.name , repo.id)
                    }
                    job1.join()
                    val job2 = launch(Dispatchers.IO){
                        repository.addCommitsBackground(repo.owner.login , repo.name , repo.id)
                    }
                    job2.join()
                    val job3 = launch(Dispatchers.IO){
                        repository.addPullRequestsBackground(repo.owner.login , repo.name , repo.id)
                    }
                    job3.join()
                    val job4 = launch(Dispatchers.IO){
                        repository.addReleasesBackground(repo.owner.login , repo.name , repo.id)
                    }
                    job4.join()
                }
            }
        }
    }


}