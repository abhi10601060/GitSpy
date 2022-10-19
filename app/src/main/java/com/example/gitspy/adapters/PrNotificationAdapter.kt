package com.example.gitspy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gitspy.R
import com.example.gitspy.models.pulls.PullRequestsItem

class PrNotificationAdapter :ListAdapter<PullRequestsItem , PrNotificationAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pr_nonotification_layout , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pr = getItem(position)

        holder.desc.text = pr.title.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val desc : TextView = itemView.findViewById(R.id.prNotification_desc)

    }

    class DiffUtilCallBack() : DiffUtil.ItemCallback<PullRequestsItem>(){
        override fun areItemsTheSame(
            oldItem: PullRequestsItem,
            newItem: PullRequestsItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PullRequestsItem,
            newItem: PullRequestsItem
        ): Boolean {
            return oldItem==newItem
        }

    }


}