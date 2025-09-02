package com.cursosant.insurance.pdfModule.model

import android.content.Context
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.FileDownloader
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.pdfModule.model
 * Created by Alain Nicolás Tello on 07/06/23 at 12:49
 * All rights reserved 2023.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
class PdfManager @Inject constructor(@ApplicationContext private val context: Context,
                                     private val downloader: FileDownloader) {
    suspend fun downloadPdf(fileUrl: String, fileName: String) = withContext(Dispatchers.IO){
        val pdfPath: String = context.filesDir.path
        val folder = File(pdfPath, Constants.PATH_APP_FILE) // FIXME: replace constant for argument
        folder.mkdir()

        val pdfFile = File(folder, fileName)

        try {
            pdfFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        downloader.downloadFile(fileUrl, pdfFile)
    }
}