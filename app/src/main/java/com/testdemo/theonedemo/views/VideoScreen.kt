package com.testdemo.theonedemo.views

import android.Manifest
import android.app.Activity
import com.testdemo.theonedemo.ExoPlayer.ExoPlayerBuildSource
import com.google.android.exoplayer2.ui.PlayerView
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import com.testdemo.theonedemo.views.VideoScreen
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.testdemo.theonedemo.R
import net.francescogatto.kkdownloadfile.utils.globalContext
import java.io.File

class VideoScreen : AppCompatActivity() {
    var exoPlayerBuildSource: ExoPlayerBuildSource? = null
    var playerView: PlayerView? = null
    var videotitle = "";

    /**
     * Checks if the app has permission to write to device storage
     *
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }else{
            val file: File = File(
                globalContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                videotitle + ".mp4"
            )
            exoPlayerBuildSource = ExoPlayerBuildSource(this, playerView, file)
            exoPlayerBuildSource!!.startVideo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_screen)
        playerView = findViewById(R.id.player_view)
        videotitle = intent.getStringExtra("title").toString();
        verifyStoragePermissions(this@VideoScreen)

//        String strVideoPath = Environment.getExternalStorageDirectory().toString() + "/DCIM/" + "Child" + ".mp4";
//        File video = new File(strVideoPath);
//        exoPlayerBuildSource = new ExoPlayerBuildSource(this, playerView, video);
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        exoPlayerBuildSource?.stopVideo()
    }

    // full screen stuff
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val decorView = window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    companion object {
        // Storage Permissions
        private const val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val file: File = File(
                globalContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                videotitle + ".mp4"
            )
            exoPlayerBuildSource = ExoPlayerBuildSource(this, playerView, file)
            exoPlayerBuildSource!!.startVideo()
        }
    }
}