package com.example.getgithubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getgithubuser.R
import com.example.getgithubuser.adapter.ListFollAdapter
import com.example.getgithubuser.dataClass.GithubUser
import com.example.getgithubuser.viewmodel.FollowersViewModel
import com.example.getgithubuser.viewmodel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.fragment_following.progressBar


class FollowingFragment : Fragment() {



    private lateinit var adapter : ListFollAdapter
    private lateinit var followingViewModel : FollowingViewModel




    companion object {
        private val ARG_FOLLOWING = "arg_following"


        fun newInstance(username : String) : FollowingFragment{
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_FOLLOWING,username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rvFollowing.setHasFixedSize(true)

        var username = arguments?.getString(ARG_FOLLOWING)

        adapter = ListFollAdapter()
        adapter.notifyDataSetChanged()
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        rvFollowing.adapter = adapter
//


        showLoading(true)
        showDataFollowing(username.toString())



    }


   private fun showDataFollowing(username: String){

        followingViewModel = ViewModelProvider(this , ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        followingViewModel.setDataFollowing(username)
        followingViewModel.getDataFollowing().observe(viewLifecycleOwner, Observer { following ->

            if ( following != null ) {
                adapter.setData(following)
                showLoading(false)
            }

        })
    }


    private fun showLoading(state : Boolean){

        if(state){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }

    }




}