package com.cursosant.insurance.pdfModule.view

import android.content.Intent
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosant.insurance.R
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.FileDownloader
import com.cursosant.insurance.common.utils.TypeError
import com.cursosant.insurance.common.utils.UiUtils
import com.cursosant.insurance.databinding.FragmentPdfBinding
import com.cursosant.insurance.pdfModule.view.adapters.PdfPageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
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

    private var parcelFileDescriptor: ParcelFileDescriptor? = null
    private var pdfRenderer: PdfRenderer? = null
    private var pdfAdapter: PdfPageAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPdfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupArguments()
        setupAppBar()
        setupButtons()
        setupPdfList()
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

    private fun setupPdfList() {
        binding.pdfRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
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
        if (!name.endsWith(Constants.SUFFIX_PDF, ignoreCase = true)) {
            utils.snackbarShort(binding.root, TypeError.PDF_COMPAT.resMsg)
            return
        }

        val pdfFile = getPdf()
        if (!pdfFile.exists()) {
            utils.snackbarShort(binding.root, TypeError.PDF_LOAD.resMsg)
            return
        }

        binding.pdfRecyclerView.adapter = null
        releaseRenderer()

        val descriptor = try {
            ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
        } catch (e: IOException) {
            utils.snackbarShort(binding.root, TypeError.PDF_LOAD.resMsg)
            return
        }

        val renderer = try {
            PdfRenderer(descriptor)
        } catch (e: IOException) {
            utils.snackbarShort(binding.root, TypeError.PDF_LOAD.resMsg)
            try {
                descriptor.close()
            } catch (_: IOException) {
            }
            return
        }

        parcelFileDescriptor = descriptor
        pdfRenderer = renderer

        if (renderer.pageCount == 0) {
            utils.snackbarShort(binding.root, TypeError.PDF_LOAD.resMsg)
            releaseRenderer()
            return
        }

        pdfAdapter = PdfPageAdapter(renderer).also {
            binding.pdfRecyclerView.adapter = it
        }
        binding.pdfRecyclerView.scrollToPosition(0)
    }

    override fun onDestroyView() {
        binding.pdfRecyclerView.adapter = null
        releaseRenderer()
        _binding = null
        super.onDestroyView()
    }

    protected fun setupProvider(dir: String, path: String) {
        providerDir = dir
        pathAppFile = path
    }

    private fun releaseRenderer() {
        pdfAdapter?.clear()
        pdfAdapter = null

        pdfRenderer?.close()
        pdfRenderer = null

        try {
            parcelFileDescriptor?.close()
        } catch (_: IOException) {
        } finally {
            parcelFileDescriptor = null
        }
    }
}
