package com.testdemo.theonedemo.models

import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import net.francescogatto.kkdownloadfile.utils.globalContext
import java.io.File

class Video {
    @SerializedName("description")
    @Expose
    var description: String ? = null

    @SerializedName("sources")
    @Expose
    var sources: List<String>? = null

    @SerializedName("subtitle")
    @Expose
    var subtitle: String? = null

    @SerializedName("thumb")
    @Expose
    var thumb: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    var isDownloading: Boolean = false
    var progress = 0

    val uriFile: Uri
        get() = file.toUri()
    val file: File
        get() = File(globalContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), title+".mp4")
}