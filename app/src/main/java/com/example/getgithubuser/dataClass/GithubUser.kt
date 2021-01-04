package com.example.getgithubuser.dataClass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class GithubUser (

      var id: Int = 0,
      var username: String? = null,
      var name: String? = null,
      var avatar: String? = null,
      var followers: String? = null,
      var following: String? = null,
      var repository: String? = null,
      var company : String? = null,
      var bio : String? = null,
      var location : String? = null,
      var language : String? = null



 ) : Parcelable