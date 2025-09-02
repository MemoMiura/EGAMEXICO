package com.cursosant.insurance.policyDetailModule.view.adapters

import androidx.recyclerview.widget.DiffUtil
import com.cursosant.insurance.common.entities.FilePDF
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policyDetailModule.view.adapters
 * Created by Alain Nicolás Tello on 04/06/23 at 16:03
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
class FilePDFDiff @Inject constructor() : DiffUtil.ItemCallback<FilePDF>() {
    override fun areItemsTheSame(oldItem: FilePDF, newItem: FilePDF) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FilePDF, newItem: FilePDF) = oldItem == newItem
}