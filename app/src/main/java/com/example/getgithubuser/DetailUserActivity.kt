package com.example.getgithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.getgithubuser.adapter.SectionsPagerAdapter
import com.example.getgithubuser.dataClass.GithubUser
import com.example.getgithubuser.databinding.ActivityDetailUserBinding
import com.example.getgithubuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.activity_main.*




class DetailUserActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailViewModel : DetailViewModel


    companion object {
       const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title ="Detail"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val user = intent.getParcelableExtra(EXTRA_USER) as GithubUser



        //        TAB_LAYOUT_ADAPTER
        val sectionsAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsAdapter.username = user?.username
        view_pager.adapter = sectionsAdapter
        tl_tabs_.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f



        setUserDetail()


    }


    private fun setUserDetail(){

        val user = intent.getParcelableExtra(EXTRA_USER) as GithubUser

        detailViewModel = ViewModelProvider(this , ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(user.username.toString())
        detailViewModel.getDetailUser().observe( this , Observer { itemUser ->


            if( itemUser != null ){
                Glide.with(this@DetailUserActivity)
                    .load(itemUser.avatar)
                    .apply(RequestOptions().override(350,350))
                    .into(imgAvatarDetail)
                tvUsernameDetail.text = "@${itemUser.username}"
                tvNameDetail.text = itemUser.name
                tvBioDetail.text = itemUser.bio

            }


        })


    }


}