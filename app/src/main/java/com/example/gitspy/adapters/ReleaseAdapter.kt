package com.example.gitspy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gitspy.R
import com.example.gitspy.models.releases.ReleaseItem

class ReleaseAdapter() : ListAdapter<ReleaseItem , ReleaseAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.commit_layout , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val release = getItem(position)
        holder.desc.text = release.name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val desc : TextView = itemView.findViewById(R.id.commit_desc)
    }


    class DiffUtilCallBack() : DiffUtil.ItemCallback<ReleaseItem>(){

        override fun areItemsTheSame(oldItem: ReleaseItem, newItem: ReleaseItem): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReleaseItem, newItem: ReleaseItem): Boolean {
            return  oldItem == newItem
        }

    }


}