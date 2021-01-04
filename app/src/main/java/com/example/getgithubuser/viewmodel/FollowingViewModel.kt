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

class FollowingViewModel : ViewModel() {


    private val dataDetail = MutableLiveData<ArrayList<GithubUser>>()
    var itemUser = ArrayList<GithubUser>()


    fun setDataFollowing(username : String?) {

        val url = "https://api.github.com/users/${username}/following"
        val apiKey="7c93021388eec20a92403ec0e61f35ea3af601fa"

        val client = AsyncHttpClient()

        client.addHeader("Authorization", "${apiKey}")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {

                val result = String(responseBody)
                try {
                    val followings = JSONArray(result)

                    for (i in 0 until followings.length()) {
                        val following = followings.getJSONObject(i)
                        val username = following.getString("login")


                        Log.d("Data-Following" , username)
                        setDetailUserInFollowing(username)



                    }



                } catch (e: Exception) {
                    e.printStackTrace()

                }


            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("error no Data Following" , error?.message.toString())
            }
        })
    }


    fun getDataFollowing() : LiveData<ArrayList<GithubUser>> {
        return dataDetail
    }



    fun setDetailUserInFollowing(username: String?){

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

                }catch (e : Exception){
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