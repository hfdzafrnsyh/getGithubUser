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
import org.json.JSONObject
import java.lang.Exception

class FollowersViewModel : ViewModel()  {


    private val dataDetail = MutableLiveData<ArrayList<GithubUser>>()
    var itemUser = ArrayList<GithubUser>()


     fun setDataFollower(username : String) {

        val url = "https://api.github.com/users/${username}/followers"
        val apiKey="7c93021388eec20a92403ec0e61f35ea3af601fa"

         val client = AsyncHttpClient()
        client.addHeader("Authorization", "token ${apiKey}")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {

                val result = String(responseBody)
                try {
                    val followers = JSONArray(result)

                    for (i in 0 until followers.length()) {
                        val follower = followers.getJSONObject(i)
                        val username = follower.getString("login")


                        Log.d("Data-Followers" , username)
                        setDetailUserInFollowers(username)



                    }



                } catch (e: Exception) {
                    e.printStackTrace()

                }


            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {

            }
        })
    }


    fun getDataFollower() : LiveData<ArrayList<GithubUser>> {
        return dataDetail
    }



    fun setDetailUserInFollowers(username: String){

        val url ="https://api.github.com/users/${username}"
        val apiKey="7c93021388eec20a92403ec0e61f35ea3af601fa"

        val client = AsyncHttpClient()
        client.addHeader("Authorization" , "token ${apiKey}")
        client.addHeader("User-Agent" , "request")
        client.get(url , object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)

                    val user = GithubUser()
                    user.name = responseObject.getString("name")
                    user.username = responseObject.getString("login")
                    user.avatar = responseObject.getString("avatar_url")


                    itemUser.add(user)

                    dataDetail.postValue(itemUser)

                }catch (e :Exception ){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Error No data Follower" , error?.message.toString())
            }

        })

    }
}