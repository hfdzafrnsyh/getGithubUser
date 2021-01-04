package com.example.getgithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getgithubuser.dataClass.GithubUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception


class RepoViewModel : ViewModel() {

    private val dataDetail = MutableLiveData<ArrayList<GithubUser>>()
    var repoUser = ArrayList<GithubUser>()


    fun setRepoUser(username : String){

        val url = "https://api.github.com/users/${username}/repos"
        val apiKey="7c93021388eec20a92403ec0e61f35ea3af601fa"

        val client = AsyncHttpClient()

        client.addHeader("Authorization" , "token ${apiKey}")
        client.addHeader("User-Agent" , "request")
        client.get(url, object :  AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {

                val result = String(responseBody)
                try {

                    val repoData = JSONArray(result)

                    for( i in 0 until repoData.length()){
                        val repo = repoData.getJSONObject(i)
                        Log.d("Data-Repo" , result)
                        val userDetail = GithubUser()

                        userDetail.name = repo.getString("name")
                        userDetail.language = repo.getString("language")


                        repoUser.add(userDetail)

                    }

                    dataDetail.postValue(repoUser)

                }catch ( e : Exception ){
                        e.printStackTrace()
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                TODO("Not yet implemented")
            }

        })

    }


    fun getDataRepository() : LiveData<ArrayList<GithubUser>> {
        return dataDetail
    }


}