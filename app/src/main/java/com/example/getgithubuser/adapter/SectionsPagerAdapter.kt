package com.example.getgithubuser.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.getgithubuser.R
import com.example.getgithubuser.fragment.FollowersFragment
import com.example.getgithubuser.fragment.FollowingFragment
import com.example.getgithubuser.fragment.RepositoryFragment
import org.jetbrains.annotations.Nullable

class SectionsPagerAdapter(private val mContext : Context , fm : FragmentManager) : FragmentPagerAdapter(fm , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var username : String? = null



    private val TAB_TITLES = intArrayOf(R.string.tab_1 , R.string.tab_2 , R.string.tab_repo)

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment.newInstance(username.toString())
            1 -> fragment = FollowingFragment.newInstance(username.toString())
            2 -> fragment = RepositoryFragment.newInstance(username.toString())
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString((TAB_TITLES[position]))
    }


    fun username(usernameUser : String) {
        this.username = usernameUser
    }
}