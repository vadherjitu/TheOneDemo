package com.testdemo.theonedemo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("videos")
    @Expose
    var videos: List<Video>? = null
}