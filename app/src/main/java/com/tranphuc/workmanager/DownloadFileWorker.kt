package com.tranphuc.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class DownloadFileWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {
    override fun doWork(): Result {
        try {
            var count = 0
            val url =
                URL("https://tophinhnen.com/wp-content/uploads/2018/08/hinh-anh-thien-nhien-kich-thuoc-lon-1.jpg")
            val connection = url.openConnection()
            connection.connect()

            val lenghtOfFile = connection.contentLength

            val input = BufferedInputStream(url.openStream(), 8192)

            var storageDir = applicationContext.getExternalFilesDir(null)!!.toString() + File.separator
            val fileName = "test"
            val imageFile = File(storageDir + fileName)
            val output = FileOutputStream(imageFile)

            val data = ByteArray(1024)
            var total: Long = 0

            while ({ count = input.read(data); count }() != -1) {

                total += count.toLong()
                Log.d("debug_phuc", "percent: " + (total * 100 / lenghtOfFile).toInt())
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()

        } catch (e: Exception) {
            Log.e("debug_phuc: ", e.message)
            return Result.failure()
        }

        return Result.success()
    }
}