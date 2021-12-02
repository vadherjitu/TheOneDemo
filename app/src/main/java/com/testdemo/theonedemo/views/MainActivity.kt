package com.testdemo.theonedemo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoprac.grubbrrcafe.viewmodel.CommanViewModel
import com.demoprac.grubbrrcafe.viewmodel.ViewModelProviderFactory
import com.testdemo.theonedemo.R
import com.testdemo.theonedemo.adapter.VideosRecyclerAdapter
import com.testdemo.theonedemo.databinding.ActivityMainBinding
import com.testdemo.theonedemo.models.Video
import io.ktor.client.*

class MainActivity : AppCompatActivity(), VideosRecyclerAdapter.RecyclerViewClickListener {
    lateinit var mainViewModel: CommanViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var videosRecyclerAdapter: VideosRecyclerAdapter
    private lateinit var videoList: ArrayList<Video>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val factory = ViewModelProviderFactory(application)
        mainViewModel = ViewModelProvider(this, factory).get(CommanViewModel::class.java)
        mainViewModel.getData();
        mainViewModel.lst.observe(this, Observer {
            Log.i("data", it.toString())
            videoList = it;
            videosRecyclerAdapter = VideosRecyclerAdapter(mainViewModel, this, this, videoList)
            binding.recyclerVideolst.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = videosRecyclerAdapter
            }
        })
        setContentView(binding.root)
    }

    override fun onClick(position: Int) {
        Toast.makeText(this,"Download Start "+videoList.get(position).title,Toast.LENGTH_SHORT).show()
        mainViewModel.downloadWithFlow(videoList.get(position));
        mainViewModel.isDownloaded.observe(this, {
            if (it) {
                Toast.makeText(this,"Download Success "+videoList.get(position).title,Toast.LENGTH_SHORT).show()
                videosRecyclerAdapter.setDownloading(videoList.get(position), true);
            }
        }
        )
    }
}