package com.example.gitspy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

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

    }

    class DiffutilCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return  oldItem==newItem
        }

    }




}