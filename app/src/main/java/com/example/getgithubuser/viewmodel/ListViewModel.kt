package com.example.getgithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getgithubuser.dataClass.GithubUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*

class ListViewModel : ViewModel () {


    private val listUserItem = MutableLiveData<ArrayList<GithubUser>>()

    fun setDataUser(username : String) {

         val listGetUser = ArrayList<GithubUser>()

        val client = AsyncHttpClient()
        val apiKey = "7c93021388eec20a92403ec0e61f35ea3af601fa"

        val url = "https://api.github.com/search/users?q=${username}"


        client.addHeader("Authorization", "token ${apiKey}")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {


                try {


                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")
                    Log.d("list ViewModel" , result)

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val user = GithubUser()

                        user.id = item.getInt("id")
                        user.username = item.getString("login")
                        user.name = item.getString("login")
                        user.avatar = item.getString("avatar_url")
                        user.followers = item.getString("followers_url")
                        user.following = item.getString("following_url")


                        listGetUser.add(user)
                    }


                    listUserItem.postValue(listGetUser)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    e.printStackTrace()
                }


            }


            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Error No data Listview", error?.message.toString())
            }


        })



    }


    fun getDataUser(): LiveData<ArrayList<GithubUser>> {
        return listUserItem
    }

}
