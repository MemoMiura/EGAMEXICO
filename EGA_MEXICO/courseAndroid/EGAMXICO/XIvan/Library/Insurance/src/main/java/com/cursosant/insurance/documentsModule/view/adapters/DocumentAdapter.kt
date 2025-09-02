package com.cursosant.insurance.documentsModule.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Document
import com.cursosant.insurance.databinding.ItemFilePdfBinding
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.documentsModule.view.adapters
 * Created by Alain Nicolás Tello on 07/06/23 at 15:18
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
class DocumentAdapter @Inject constructor(diff: DocumentDiff) :
    ListAdapter<Document, RecyclerView.ViewHolder>(diff){

    private lateinit var listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_file_pdf, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val document = getItem(position)
        with(holder as ViewHolder){
            setListener(document)
            binding?.let {
                it.setVariable(BR.document, document)
                it.executePendingBindings()
            }
        }
    }

    fun setOnClickListener(listener: OnClickListener){
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = DataBindingUtil.bind<ItemFilePdfBinding>(view)

        fun setListener(document: Document) {
            binding?.root?.setOnClickListener { listener.onClick(document) }
        }
    }
}