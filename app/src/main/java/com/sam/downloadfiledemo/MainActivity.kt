package com.sam.downloadfiledemo

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var myDownloadid: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnDownload = findViewById<Button>(R.id.btn_download)
        btnDownload.setOnClickListener {
            var request = DownloadManager.Request(
                Uri.parse("https://atmiyauni.ac.in/wp-content/uploads/2020/04/AU-Brochure-update-March-2020.pdf")
            )
                .setTitle(" Atmiya College Brouchure 2020")
                .setDescription("Atmiya Brouchure Downloading... ")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)

            var dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            myDownloadid = dm.enqueue(request)
        }

        var br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == myDownloadid) {
                    Toast.makeText(
                        applicationContext,
                        "Kcc Brouchure Download Complete",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
}