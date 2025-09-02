package com.cursosant.insurance.common.utils

import com.cursosant.insurance.common.entities.InsuranceException
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

/**
 * Created by oscarzarco on 06/02/18.
 * Updated by Alain Nicolás Tello on 07/06/2023
 */
class FileDownloader @Inject constructor() {
    private val MB = 1024 * 1024

    fun downloadFile(fileUrl: String?, directory: File?) {
        try {
            val url = URL(fileUrl)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()
            val inputStream = urlConnection.inputStream
            val fileOutputStream = FileOutputStream(directory)
            val buffer = ByteArray(MB)
            var bufferLength: Int
            while (inputStream.read(buffer).also { bufferLength = it } > 0) {
                fileOutputStream.write(buffer, 0, bufferLength)
            }
            fileOutputStream.close()
        } catch (e: Exception){
            throw InsuranceException(TypeError.PDF_DOWNLOAD)
        }
    }
}