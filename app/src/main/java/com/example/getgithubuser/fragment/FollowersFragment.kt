package com.example.getgithubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getgithubuser.R
import com.example.getgithubuser.adapter.ListFollAdapter
import com.example.getgithubuser.dataClass.GithubUser
import com.example.getgithubuser.viewmodel.FollowersViewModel
import kotlinx.android.synthetic.main.follow_list.*
import kotlinx.android.synthetic.main.fragment_followers.*
import org.json.JSONArray
import java.lang.Exception


class FollowersFragment : Fragment() {





    private lateinit var adapter : ListFollAdapter
    private lateinit var followersViewModel : FollowersViewModel



    companion object {
        private  val ARG_FOLLOWER = "arg_follower"



        @JvmStatic
        fun newInstance(username : String) : FollowersFragment{
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_FOLLOWER,username)
            fragment.arguments = bundle
            return fragment
        }
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollower.setHasFixedSize(true)

        var username = arguments?.getString(ARG_FOLLOWER)

        adapter = ListFollAdapter()
        adapter.notifyDataSetChanged()
        rvFollower.layoutManager = LinearLayoutManager(activity)
        rvFollower.adapter = adapter


        showLoading(true)
        showDataFollower(username.toString())



    }




 private fun showDataFollower(username: String){



        followersViewModel = ViewModelProvider(this , ViewModelProvider.NewInstanceFactory())
            .get(FollowersViewModel::class.java)
        followersViewModel.setDataFollower(username)
        followersViewModel.getDataFollower().observe(viewLifecycleOwner, Observer { follower ->

            if (follower != null ) {
                adapter.setData(follower)
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

