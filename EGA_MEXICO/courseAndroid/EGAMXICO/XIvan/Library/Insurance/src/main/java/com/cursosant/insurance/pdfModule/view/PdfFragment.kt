package com.cursosant.insurance.pdfModule.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cursosant.insurance.R
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.FileDownloader
import com.cursosant.insurance.common.utils.TypeError
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentPdfBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.pdfModule.view
 * Created by Alain Nicolás Tello on 06/06/23 at 19:34
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
@AndroidEntryPoint
open class PdfFragment : Fragment(){
    @Inject lateinit var utils: UiUtils
    @Inject lateinit var downloader: FileDownloader

    private var _binding: FragmentPdfBinding? = null
    private val binding get() = _binding!!

    private lateinit var name: String
    private lateinit var url: String
    private lateinit var providerDir: String
    private lateinit var pathAppFile: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPdfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupArguments()
        setupAppBar()
        setupButtons()
    }

    private fun setupArguments() {
        name = requireArguments().getString(Constants.ARG_NAME, "")
        url = requireArguments().getString(Constants.ARG_URL, "")
    }

    private fun setupAppBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = name//args.name
    }

    private fun setupButtons() {
        binding.fabShare.setOnClickListener {
            shareFile()
        }
    }

    private fun shareFile() {// FIXME: Couldn't find meta-data for provider with authority com.cursosant.insurance.fileprovider
        val uri = FileProvider.getUriForFile(
            requireContext(),
            providerDir,
            getPdf()
        )

        val share = Intent().apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, uri)
            action = Intent.ACTION_SEND
            type = "application/pdf"
        }

        startActivity(Intent.createChooser(share, getString(R.string.pdf_chooser_title)))
    }

    private fun getPdf() = File(requireActivity().filesDir, "${pathAppFile}${name}")

    override fun onResume() {
        super.onResume()
        // fixme: change library or make custom pdf loader
        //binding.viewModel?.downloadPdf(args.url, args.name)
        lifecycleScope.launch {
            val isDownloaded = async { downloadPdf(url, name) }
            if (isDownloaded.await()){
                utils.snackbarShort(binding.root, R.string.pdf_download_success)
                setupPDFViewer()
            } else {
                utils.snackbarLong(binding.root, TypeError.PDF_DOWNLOAD.resMsg)
            }
        }
    }

    private suspend fun downloadPdf(fileUrl: String, fileName: String): Boolean {
        utils.snackbarShort(binding.root, R.string.pdf_load_in_progress)
        val pdfPath: String = requireActivity().filesDir.path
        val folder = File(pdfPath, pathAppFile)
        folder.mkdir()

        val pdfFile = File(folder, fileName)

        return try {
            withContext(Dispatchers.IO) {
                pdfFile.createNewFile()
                downloader.downloadFile(fileUrl, pdfFile)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun setupPDFViewer() {
        binding.pdfView
            .fromFile(getPdf())
            .enableDoubletap(true)
            .scrollHandle(null)
            .onError {
                if (name.endsWith(Constants.SUFFIX_PDF)) {
                    utils.snackbarShort(binding.root, TypeError.PDF_LOAD.resMsg)
                } else {
                    utils.snackbarShort(binding.root, TypeError.PDF_COMPAT.resMsg)
                }
            }
            .load()

        binding.pdfView.apply {
            maxZoom = 4f
            midZoom = 2f
            minZoom = 1f
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun setupProvider(dir: String, path: String) {
        providerDir = dir
        pathAppFile = path
    }
}