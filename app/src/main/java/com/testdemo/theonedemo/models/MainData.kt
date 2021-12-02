package com.testdemo.theonedemo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainData {
    @SerializedName("categories")
    @Expose
    var categories: List<Category>? = null
}