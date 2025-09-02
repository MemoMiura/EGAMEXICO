package com.cursosant.insurance.quoteResultDetailModule.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cursosant.insurance.common.entities.QuotePayFrequency
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.quoteResultDetailModule.view.adapter
 * Created by Alain Nicolás Tello on 02/08/23 at 19:52
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
class QuotePayFrequencyDiff @Inject constructor() : DiffUtil.ItemCallback<QuotePayFrequency>(){
    override fun areItemsTheSame(oldItem: QuotePayFrequency, newItem: QuotePayFrequency) = newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: QuotePayFrequency, newItem: QuotePayFrequency) = newItem == oldItem
}