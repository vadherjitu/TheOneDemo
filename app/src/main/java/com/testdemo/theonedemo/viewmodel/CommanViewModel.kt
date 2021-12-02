package com.demoprac.grubbrrcafe.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.demoprac.grubbrrcafe.util.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.testdemo.theonedemo.adapter.VideosRecyclerAdapter
import com.testdemo.theonedemo.models.DownloadResult
import com.testdemo.theonedemo.models.MainData
import com.testdemo.theonedemo.models.Video
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.francescogatto.kkdownloadfile.utils.downloadFile
import org.koin.android.ext.android.inject

class CommanViewModel(
    var app: Application,
) : AndroidViewModel(app) {
    var lst = MutableLiveData<ArrayList<Video>>()
    val ktor: HttpClient by app.inject()
    var isDownloaded = MutableLiveData<Boolean>()
    fun getData() {
        val jsonFileString = Utils.getJsonDataFromAsset(app, "movies.json")
        Log.i("data", "" + jsonFileString)

        val gson = Gson()
        val listPersonType = object : TypeToken<MainData>() {}.type

        var mainData: MainData = gson.fromJson(jsonFileString, listPersonType)
        lst.value = mainData.categories?.get(0)?.videos as ArrayList<Video>?
    }

    fun downloadWithFlow(dummy: Video) {
        CoroutineScope(Dispatchers.IO).launch {
            dummy.sources?.get(0)?.let {
                ktor.downloadFile(dummy.file, it).collect {
                    withContext(Dispatchers.Main) {
                        when (it) {
                            is DownloadResult.Success -> {
                                isDownloaded.value = true
                            }
                            is DownloadResult.Error -> {
                                isDownloaded.value= false
                            }
                            is DownloadResult.Progress -> {
                                isDownloaded.value= false
                            }
                        }
                    }
                }
            }
        }
    }


}