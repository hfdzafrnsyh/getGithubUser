package com.example.getgithubuser.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.getgithubuser.dataClass.GithubUser
import com.example.getgithubuser.R
import com.example.getgithubuser.databinding.ItemListBinding
import java.util.*



class ListViewAdapter : RecyclerView.Adapter<ListViewAdapter.ListViewHolder>() {

    private val listUser =  ArrayList<GithubUser>()



    private  lateinit  var onItemClickCallBack : OnItemClickCallBack


    fun setOnClickCallback( onClickCallBack : OnItemClickCallBack){
        this.onItemClickCallBack = onClickCallBack
    }



    interface OnItemClickCallBack{
        fun setClick( data : GithubUser)
    }

    fun setData(items: ArrayList<GithubUser>){
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }



    fun addItem(item: GithubUser) {
       listUser.add(item)
        notifyDataSetChanged()
    }

    fun clearData() {
        listUser.clear()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent , false)
        return ListViewHolder(mView)
    }


    override fun onBindViewHolder(listViewHolder: ListViewHolder, position: Int) {
      listViewHolder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size



    inner  class ListViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val binding =  ItemListBinding.bind(itemView)

        fun bind(githubUser: GithubUser){

          with(binding){
              Glide.with(itemView.context)
                  .load(githubUser.avatar)
                  .apply(RequestOptions().override(55, 55))
                  .into(imgItemAvatar)

              tvItemUsername.text = "@${githubUser.username}"
              tvItemName.text = githubUser.name


              itemView.setOnClickListener {
                  onItemClickCallBack.setClick(githubUser)
              }
          }
        }


    }




}

