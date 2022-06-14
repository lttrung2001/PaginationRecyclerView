package com.ltbth.paginationrecyclerview

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val users: List<User>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isLoadingAdd = false
    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING = 2
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUser: ImageView = itemView.findViewById(R.id.img_user)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
            UserViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading,parent,false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_ITEM) {
            val user: User = users[position]
            val userViewHolder: UserViewHolder = holder as UserViewHolder
            userViewHolder.apply {
                imgUser.setImageResource(user.img)
                tvName.text = user.name
            }
        }
    }

    override fun getItemViewType(position: Int): Int
    = if (position == users.size - 1 && isLoadingAdd) {
        TYPE_LOADING
    } else {
        TYPE_ITEM
    }

    override fun getItemCount(): Int = users.size

    fun addLoading() {
        isLoadingAdd = true
        val list = users as ArrayList<User>
        list.add(User(0,""))
    }

    fun removeLoading() {
        isLoadingAdd = false
        val list = users as ArrayList<User>
        val pos = itemCount-1
        list.removeAt(pos)
        notifyItemRemoved(pos)
    }
}