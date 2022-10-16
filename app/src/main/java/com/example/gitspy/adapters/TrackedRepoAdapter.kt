package com.example.gitspy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitspy.R
import com.example.gitspy.models.Item

class TrackedRepoAdapter(private val context: Context): ListAdapter<Item, TrackedRepoAdapter.ViewHolder>(DiffutilCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tracked_repos_layout , parent ,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        Glide.with(context)
            .load(repo.owner.avatar_url)
            .into(holder.logo)

        holder.repoName.text = repo.name
        holder.owner.text = repo.owner.login
        holder.repoName.text = repo.name
        holder.owner.text = repo.owner.login
        holder.desc.text = repo.description
        holder.stars.text = "${repo.stargazers_count} stars"
        repo.language.let {
            holder.language.text = repo.language
        }
        holder.forks.text = "${repo.forks_count} Forks"

        holder.cancel.setOnClickListener(View.OnClickListener {
            cancelListener?.let {
                it(repo)
            }
        })
        holder.issues.text = repo.issue_events_count.toString()
        holder.prs.text = repo.pull_requests_count.toString()
        holder.commits.text = repo.commits_count.toString()
        holder.releases.text = repo.releases_count.toString()

        if(repo.unseen_issue_events_count >0 ){
            holder.issueCount.text = repo.unseen_issue_events_count.toString()
        }
        else{
            holder.issueCountRL.visibility = View.GONE
        }

        if(repo.unseen_commits_count >0 ){
            holder.commitsCount.text = repo.unseen_commits_count.toString()
        }
        else{
            holder.commitsCountRL.visibility = View.GONE
        }

        if(repo.unseen_pull_requests_count >0 ){
            holder.prCount.text = repo.unseen_pull_requests_count.toString()
        }
        else{
            holder.prCountRL.visibility = View.GONE
        }

        if(repo.unseen_releases_count >0 ){
            holder.releasesCount.text = repo.unseen_releases_count.toString()
        }
        else{
            holder.releasesCountRL.visibility = View.GONE
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val logo : ImageView = itemView.findViewById(R.id.trackedRepoLogoImg)
        val repoName : TextView = itemView.findViewById(R.id.trackedRepoNameText)
        val owner : TextView = itemView.findViewById(R.id.trackedRepoOwnerNameText)
        val stars : TextView = itemView.findViewById(R.id.trackedRepoStarsText)
        val language : TextView = itemView.findViewById(R.id.trackedRepoLanguageText)
        val forks : TextView = itemView.findViewById(R.id.trackedRepoForksText)
        val desc : TextView = itemView.findViewById(R.id.trackedRepoDescText)
        val issues : TextView = itemView.findViewById(R.id.trackedIssues)
        val commits : TextView = itemView.findViewById(R.id.trackedCommits)
        val releases : TextView = itemView.findViewById(R.id.trackedReleases)
        val prs : TextView = itemView.findViewById(R.id.trackedPR)
        val cancel : ImageView = itemView.findViewById(R.id.trackedRepoCancelBtn)

        val issueCountRL : RelativeLayout = itemView.findViewById(R.id.issuesNotificationCountRL)
        val issueCount : TextView = itemView.findViewById(R.id.issueNotificationCount)
        val commitsCountRL : RelativeLayout = itemView.findViewById(R.id.commitsNotificationCountRL)
        val commitsCount : TextView = itemView.findViewById(R.id.commitsNotificationCount)
        val prCountRL : RelativeLayout = itemView.findViewById(R.id.pullRequestNotificationCountRL)
        val prCount : TextView = itemView.findViewById(R.id.pullRequestNotificationCount)
        val releasesCountRL : RelativeLayout = itemView.findViewById(R.id.releasesNotificationCountRL)
        val releasesCount : TextView = itemView.findViewById(R.id.releasesNotificationCount)

    }

    class DiffutilCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return  oldItem==newItem
        }

    }

    private var cancelListener: ((Item) -> Unit)? = null

    fun onCancelClickListener(listener : ((Item) -> Unit)) {
        cancelListener = listener
    }




}