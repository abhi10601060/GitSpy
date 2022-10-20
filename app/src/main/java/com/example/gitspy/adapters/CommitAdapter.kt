package com.example.gitspy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gitspy.R
import com.example.gitspy.models.commits.CommitListItem

class CommitAdapter : ListAdapter<CommitListItem , CommitAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.commit_layout , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commit = getItem(position)
        holder.desc.text = commit.commit.message
        holder.author.text = "Creator : " + commit.commit.committer.name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val desc : TextView = itemView.findViewById(R.id.commit_desc)
        val author : TextView = itemView.findViewById(R.id.commit_creator)
    }

    class DiffUtilCallBack() : DiffUtil.ItemCallback<CommitListItem>(){

        override fun areItemsTheSame(oldItem: CommitListItem, newItem: CommitListItem): Boolean {
            return oldItem.sha == newItem.sha
        }

        override fun areContentsTheSame(oldItem: CommitListItem, newItem: CommitListItem): Boolean{
            return oldItem == newItem
        }

    }


}