package com.cursosant.insurance.pdfModule.view.adapters

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.insurance.R
import com.cursosant.insurance.databinding.ItemPdfPageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class PdfPageAdapter(
    private val renderer: PdfRenderer
) : RecyclerView.Adapter<PdfPageAdapter.PdfPageViewHolder>() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val renderMutex = Mutex()
    private val pageCache = SparseArray<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfPageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPdfPageBinding.inflate(inflater, parent, false)
        return PdfPageViewHolder(binding)
    }

    override fun getItemCount(): Int = renderer.pageCount

    override fun onBindViewHolder(holder: PdfPageViewHolder, position: Int) {
        val cached = pageCache.get(position)
        if (cached != null) {
            holder.displayPage(position, cached)
            return
        }

        holder.prepareForRendering()
        holder.renderJob = scope.launch {
            val bitmap = renderPage(position)
            withContext(Dispatchers.Main) {
                if (!isActive) {
                    bitmap.recycle()
                    return@withContext
                }
                pageCache.put(position, bitmap)

                val currentPosition = holder.adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION && currentPosition == position) {

                    holder.displayPage(position, bitmap)
                }
            }
        }
    }

    override fun onViewRecycled(holder: PdfPageViewHolder) {
        holder.clear()
        super.onViewRecycled(holder)
    }

    fun clear() {
        scope.cancel()
        for (i in 0 until pageCache.size()) {
            pageCache.valueAt(i).recycle()
        }
        pageCache.clear()
    }

    private suspend fun renderPage(position: Int): Bitmap = renderMutex.withLock {
        val page = renderer.openPage(position)
        try {
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmap
        } finally {
            page.close()
        }
    }

    inner class PdfPageViewHolder(
        private val binding: ItemPdfPageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var renderJob: Job? = null
            set(value) {
                field?.cancel()
                field = value
            }

        init {
            binding.pdfPageView.maximumScale = 4f
            binding.pdfPageView.mediumScale = 2f
            binding.pdfPageView.minimumScale = 1f
        }

        fun displayPage(position: Int, bitmap: Bitmap) {
            binding.pdfPageView.setImageBitmap(bitmap)
            binding.pdfPageView.contentDescription = binding.root.context.getString(
                R.string.pdf_page_content_description,
                position + 1,
                renderer.pageCount
            )
        }

        fun prepareForRendering() {
            renderJob?.cancel()
            renderJob = null
            binding.pdfPageView.setImageDrawable(null)
            binding.pdfPageView.contentDescription =
                binding.root.context.getString(R.string.pdf_page_placeholder_description)
        }

        fun clear() {
            renderJob?.cancel()
            renderJob = null
            binding.pdfPageView.setImageDrawable(null)
            binding.pdfPageView.contentDescription =
                binding.root.context.getString(R.string.pdf_page_placeholder_description)
        }
    }
}
