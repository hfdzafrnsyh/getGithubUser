package com.example.getgithubuser.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getgithubuser.DetailUserActivity
import com.example.getgithubuser.dataClass.GithubUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {


    private val itemDetailUser = MutableLiveData<GithubUser>()

    fun setDetailUser(username: String) {


        val url = "https://api.github.com/users/${username}"
        val apiKey="7c93021388eec20a92403ec0e61f35ea3af601fa"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token ${apiKey}")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray) {


                try {

                    val result = String(responseBody)
                    val responseObject = JSONObject(result)


                    Log.d("Detail activity" , result)


                    val userDetail = GithubUser()

                    userDetail.id = responseObject.getInt("id")
                    userDetail.avatar = responseObject.getString("avatar_url")
                    userDetail.name = responseObject.getString("name")
                    userDetail.username = responseObject.getString("login")
                    userDetail.followers=responseObject.getString("followers_url")
                    userDetail.following=responseObject.getString("following_url")
                    userDetail.location = responseObject.getString("location")
                    userDetail.bio = responseObject.getString("bio")
                    userDetail.company = responseObject.getString("company")
                    userDetail.repository = responseObject.getString("repos_url")


                    itemDetailUser.postValue(userDetail)


                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }


            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("Error No data Detail" , error?.message.toString())

            }


        })
    }


fun getDetailUser() : LiveData<GithubUser> {
    return itemDetailUser
}


}





