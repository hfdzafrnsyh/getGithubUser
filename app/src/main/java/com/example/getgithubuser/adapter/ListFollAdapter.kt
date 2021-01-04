package com.example.getgithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.getgithubuser.R
import com.example.getgithubuser.dataClass.GithubUser
import com.example.getgithubuser.databinding.FollowListBinding
import java.util.ArrayList


class ListFollAdapter(): RecyclerView.Adapter<ListFollAdapter.ListFollViewHolder>() {

    private val dataUserList = ArrayList<GithubUser>()




    fun setData(items: ArrayList<GithubUser>){
        dataUserList.clear()
        dataUserList.addAll(items)
        notifyDataSetChanged()
    }



    fun addItem(item: GithubUser) {
        dataUserList.add(item)
        notifyDataSetChanged()
    }

    fun clearData() {
        dataUserList.clear()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollViewHolder {
       val viewList = LayoutInflater.from(parent.context).inflate(R.layout.follow_list , parent , false )
        return  ListFollViewHolder(viewList)
    }

    override fun onBindViewHolder(listFollViewHolder: ListFollViewHolder, position: Int) {
        listFollViewHolder.bind(dataUserList[position])
    }

    override fun getItemCount(): Int  = dataUserList.size




  inner  class ListFollViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

      private val binding = FollowListBinding.bind(itemView)

        fun bind(githubUserFoll: GithubUser){
         with(binding){
             Glide.with(itemView.context)
                 .load(githubUserFoll.avatar)
                 .apply(RequestOptions().override(55, 55))
                 .into(imgFollowAvatar)

             tvFollowUsername.text = "@${githubUserFoll.username}"
             tvFollowName.text = githubUserFoll.name
         }
        }

    }
}