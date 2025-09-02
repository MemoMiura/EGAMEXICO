package com.cursosant.insurance.quoteResultDetailModule.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.QuotePayFrequency
import com.cursosant.insurance.databinding.ItemPayFrequencyBinding
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.quoteResultDetailModule.view.adapter
 * Created by Alain Nicolás Tello on 02/08/23 at 19:50
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
class PayFrequencyAdapter @Inject constructor(diff: QuotePayFrequencyDiff) :
    ListAdapter<QuotePayFrequency, RecyclerView.ViewHolder>(diff){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pay_frequency, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val payFrequency = getItem(position) // TODO: move to let
        with(holder as ViewHolder) {
            binding?.let {
                it.setVariable(BR.payFrequency, payFrequency)
                it.executePendingBindings()
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemPayFrequencyBinding>(view)
    }
}