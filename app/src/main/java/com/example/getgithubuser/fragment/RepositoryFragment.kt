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
import com.example.getgithubuser.adapter.ListRepositoryAdapter
import com.example.getgithubuser.viewmodel.FollowingViewModel
import com.example.getgithubuser.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.android.synthetic.main.fragment_repository.*
import kotlinx.android.synthetic.main.fragment_repository.progressBar


class RepositoryFragment : Fragment() {


  private lateinit  var adapter : ListRepositoryAdapter
  private lateinit var repoViewModel : RepoViewModel

    companion object {
        private val ARG_repo = "arg_repo"


        fun newInstance(username : String) : RepositoryFragment{
            val fragment = RepositoryFragment()
            val bundle = Bundle()
            bundle.putString(ARG_repo,username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvRepo.setHasFixedSize(true)

        val user = arguments?.getString(ARG_repo)

        adapter = ListRepositoryAdapter()
        adapter.notifyDataSetChanged()
        rvRepo.layoutManager = LinearLayoutManager(activity)
        rvRepo.adapter = adapter

        showLoading(true)
        showDataRepository(user.toString())


    }

  private fun showDataRepository(username: String){

        repoViewModel = ViewModelProvider(this , ViewModelProvider.NewInstanceFactory()).get(
            RepoViewModel::class.java)
        repoViewModel.setRepoUser(username)
        repoViewModel.getDataRepository().observe(viewLifecycleOwner, Observer { repo ->

            if ( repo != null ) {
                adapter.setData(repo)
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