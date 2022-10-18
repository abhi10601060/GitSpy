package com.example.gitspy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gitspy.R
import com.example.gitspy.models.issues.Issue

class IssueAdapter : ListAdapter<Issue , IssueAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.issue_layout , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val issue= getItem(position)
        holder.desc.text = issue.title
        holder.author.text = "Creator : ${issue.user.login}"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val desc : TextView = itemView.findViewById(R.id.issue_desc)
        val author : TextView = itemView.findViewById(R.id.issue_creator)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Issue>() {
        override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
            return oldItem == newItem
        }

    }
}