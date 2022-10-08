package com.example.gitspy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitspy.R
import com.example.gitspy.models.Item
import com.example.gitspy.ui.fragments.RepositoryFragment


class RepoAdapter(private val context: Context) : ListAdapter<Item, RepoAdapter.ViewHolder>(DiffutilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_layout , parent , false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)

        Glide.with(context)
            .load(repo.owner.avatar_url)
            .into(holder.logo)

        holder.repoName.text = repo.name
        holder.owner.text = repo.owner.login
        holder.desc.text = repo.description
        holder.stars.text = "${repo.stargazers_count} stars"
        repo.language.let {
            holder.language.text = repo.language
        }
        holder.forks.text = "${repo.forks_count} Forks"

        holder.parent.setOnClickListener(View.OnClickListener {
            itemClickListener?.let {
                it(repo)
            }
        })

        holder.trackBtn.setOnClickListener(View.OnClickListener {
            trackClickListener?.let {
                it(repo)
            }
        })



    }

    private var itemClickListener : ((Item) -> Unit)? = null

    fun setOnItemClickListener(listener : ((Item) -> Unit)){
        itemClickListener = listener
    }

    private var trackClickListener : ((Item) -> Unit)? = null

    fun setOnTrackClickListener(listener : ( (Item) -> Unit)){
        trackClickListener = listener
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val logo : ImageView = itemView.findViewById(R.id.repoLogoImg)
        val repoName : TextView = itemView.findViewById(R.id.repoNameText)
        val owner : TextView = itemView.findViewById(R.id.ownerNameText)
        val desc :TextView = itemView.findViewById(R.id.repoDescText)
        val stars : TextView = itemView.findViewById(R.id.repoStarsText)
        val language : TextView = itemView.findViewById(R.id.repoLanguageText)
        val forks : TextView = itemView.findViewById(R.id.repoForksText)
        val trackBtn : Button = itemView.findViewById(R.id.trackRepoBtn)
        val parent : RelativeLayout = itemView.findViewById(R.id.parentRL)

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