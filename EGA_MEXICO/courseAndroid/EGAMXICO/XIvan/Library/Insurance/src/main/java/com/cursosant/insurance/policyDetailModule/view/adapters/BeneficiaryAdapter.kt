package com.cursosant.insurance.policyDetailModule.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.LifeBeneficiary
import com.cursosant.insurance.common.utils.DateUtils
import com.cursosant.insurance.databinding.ItemBeneficiaryBinding
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policyDetailModule.view.adapters
 * Created by Alain Nicolás Tello on 06/06/23 at 12:09
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
class BeneficiaryAdapter @Inject constructor(diff: BeneficiaryDiff, private val utils: DateUtils) :
    ListAdapter<LifeBeneficiary, RecyclerView.ViewHolder>(diff){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_beneficiary, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val beneficiary = getItem(position)
        with(holder as ViewHolder){
            binding?.let {
                it.setVariable(BR.beneficiary, beneficiary)
                it.setVariable(BR.utils, utils)
                it.executePendingBindings()
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = DataBindingUtil.bind<ItemBeneficiaryBinding>(view)
    }
}