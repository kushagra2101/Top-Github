package com.kushagragoel.top_github.ui.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kushagragoel.top_github.R
import com.kushagragoel.top_github.network.model.Item

class RepoListAdapter(private val clickListener: IClickListener, private val itemList: MutableList<Item>?):
    RecyclerView.Adapter<RepoListAdapter.RepoItemViewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewModel {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return RepoItemViewModel(itemView)
    }

    override fun onBindViewHolder(holder: RepoItemViewModel, position: Int) {
        val repoItem = itemList?.get(position)
        holder.bindItem(repoItem!!)
    }

    override fun getItemCount(): Int = itemList?.size?:0

    inner class RepoItemViewModel(private val view: View) : RecyclerView.ViewHolder(view) {
        private val avatarImageView = view.findViewById<ImageView>(R.id.avatarImageView)
        private val repoNameTextView = view.findViewById<TextView>(R.id.repoNameTextView)
        private val userNameTextView = view.findViewById<TextView>(R.id.userNameTextView)

        fun bindItem(item: Item) {
            view.setOnClickListener {
                clickListener.onRepoItemClick(item, avatarImageView)
            }
            if (!item.avatars.isNullOrEmpty()) {
                Glide.with(avatarImageView.context)
                    .load(item.avatars[0])
                    .apply(
                        RequestOptions()
                            .circleCrop()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(avatarImageView)
            } else {
                avatarImageView.setImageResource(R.drawable.ic_baseline_person_24)
            }
            val userRepoNameSplit = item.repo?.split('/')
            userNameTextView.text = userRepoNameSplit?.get(0)?:""
            repoNameTextView.text = userRepoNameSplit?.get(1)?:""
        }
    }

    interface IClickListener {
        fun onRepoItemClick(item: Item, imageView: ImageView)
    }

}