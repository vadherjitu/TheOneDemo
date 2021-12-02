package com.testdemo.theonedemo.adapter

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.demoprac.grubbrrcafe.util.Constants.BASE_URL_IMAGE
import com.demoprac.grubbrrcafe.viewmodel.CommanViewModel
import com.testdemo.theonedemo.BuildConfig
import com.testdemo.theonedemo.databinding.RawVideoBinding
import com.testdemo.theonedemo.models.Video
import com.testdemo.theonedemo.views.VideoScreen
import net.francescogatto.kkdownloadfile.utils.globalContext
import net.francescogatto.kkdownloadfile.utils.openFile
import java.io.File

class VideosRecyclerAdapter
constructor(
    viewModel: CommanViewModel,
    private val context: Context,
    val listener: RecyclerViewClickListener,
    private var videoList: ArrayList<Video>
) : RecyclerView.Adapter<VideosRecyclerAdapter.VideoViewHolder>() {

    interface RecyclerViewClickListener {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding =
            RawVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = videoList.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        if (!videoList.isEmpty()) {
            var video = videoList.get(position);
//            var file = File(globalContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), video.title+".mp4")
//            val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
            holder.binding.title.text = video.title
            holder.binding.ivThubnail.apply {
                holder.binding.ivThubnail.load(BASE_URL_IMAGE + video.thumb)
            }
            holder.binding.buttonDownload.setOnClickListener(View.OnClickListener {
                listener.onClick(position)
            })
            holder.binding.btnPlay.setOnClickListener(View.OnClickListener {
                context.startActivity(Intent(context, VideoScreen::class.java).putExtra("title",video.title))
            })


            if (video.isDownloading) {
                holder.binding.buttonDownload.visibility = GONE;
                holder.binding.btnPlay.visibility = VISIBLE;
            } else {
                holder.binding.buttonDownload.visibility = VISIBLE;
                holder.binding.btnPlay.visibility = GONE;
            }
        }
    }

    fun setDownloading(dummy: Video, isDownloading: Boolean) {
        getDummy(dummy)?.isDownloading = isDownloading
        notifyItemChanged(videoList.indexOf(dummy))
    }

    private fun getDummy(dummy: Video) = videoList.find { dummy.thumb == it.thumb }

    class VideoViewHolder(val binding: RawVideoBinding) :
        RecyclerView.ViewHolder(binding.root)

}