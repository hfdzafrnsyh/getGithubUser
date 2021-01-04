package com.example.getgithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.getgithubuser.R
import com.example.getgithubuser.dataClass.GithubUser
import com.example.getgithubuser.databinding.ItemListBinding
import com.example.getgithubuser.databinding.RepoListBinding
import kotlinx.android.synthetic.main.repo_list.view.*

class ListRepositoryAdapter  : RecyclerView.Adapter<ListRepositoryAdapter.ListRepositoryViewHolder>()  {

    private val listRepo = ArrayList<GithubUser>()



    fun setData(items: ArrayList<GithubUser>){
        listRepo.clear()
        listRepo.addAll(items)
        notifyDataSetChanged()
    }



    fun addItem(item: GithubUser) {
        listRepo.add(item)
        notifyDataSetChanged()
    }

    fun clearData() {
        listRepo.clear()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRepositoryViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.repo_list , parent , false)
        return ListRepositoryViewHolder(mView)
    }

    override fun onBindViewHolder(listRepositoryViewHolder: ListRepositoryViewHolder, position: Int) {
        listRepositoryViewHolder.bind(listRepo[position])
    }

    override fun getItemCount() : Int =  listRepo.size


    class ListRepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding =   RepoListBinding.bind(itemView)

        fun bind(repo: GithubUser){

            with(binding){

                tvRepoName.text = repo.name
                tvLanguageRepo.text = repo.language
            }
        }
    }
}