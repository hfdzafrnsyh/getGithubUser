package com.example.getgithubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getgithubuser.adapter.ListViewAdapter
import com.example.getgithubuser.dataClass.GithubUser
import com.example.getgithubuser.databinding.ActivityMainBinding
import com.example.getgithubuser.viewmodel.ListViewModel

import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

   private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : ListViewAdapter
    private lateinit var listViewModel : ListViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvItem.setHasFixedSize(true)

        adapter = ListViewAdapter()
        adapter.notifyDataSetChanged()


        adapter.setOnClickCallback( object  : ListViewAdapter.OnItemClickCallBack {
            override fun setClick(data: GithubUser) {
                showProccesClick(data)
            }

        })

        tv_welcome.text = resources.getString(R.string.search_welcome)

        listMainRecyclerView()


    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(username: String): Boolean {
                showLoading(true)
                listViewModel.setDataUser(username)
                Toast.makeText(this@MainActivity, " Searching ${username}", Toast.LENGTH_SHORT).show()

                return true
            }
            
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_localization){
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }



  private fun listMainRecyclerView(){


       listViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
           ListViewModel::class.java)

       binding.rvItem.layoutManager = LinearLayoutManager(this)
       binding.rvItem.adapter = adapter



       listViewModel.getDataUser().observe( this , Observer { githubUser ->
           if(githubUser != null){
               tv_welcome.text = ""
               adapter.setData(githubUser)
               showLoading(false)

           }
       })
   }

 private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


   fun showProccesClick( data : GithubUser){

       val intent = Intent(this, DetailUserActivity::class.java)
       intent.putExtra(DetailUserActivity.EXTRA_USER, data)
       startActivity(intent)

   }

}

