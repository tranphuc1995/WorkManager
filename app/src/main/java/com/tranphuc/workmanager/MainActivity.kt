package com.tranphuc.workmanager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init work manager
        var oneTimeWorkRequest = OneTimeWorkRequest.Builder(DownloadFileWorker::class.java).build()

        btnDownloadFile.setOnClickListener {
            WorkManager.getInstance().enqueue(oneTimeWorkRequest)
            finish()
        }

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer {
            Log.d("debug_phuc", "" + it.state.name)
        })
    }
}
