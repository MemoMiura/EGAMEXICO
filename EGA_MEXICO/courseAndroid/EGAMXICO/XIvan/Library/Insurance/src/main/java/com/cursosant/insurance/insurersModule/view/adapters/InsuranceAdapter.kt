package com.cursosant.insurance.insurersModule.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Insurance
import com.cursosant.insurance.databinding.ItemInsuranceBinding
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.insurersModule.view.adapters
 * Created by Alain Nicolás Tello on 03/06/23 at 11:01
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
class InsuranceAdapter @Inject constructor(diff: InsuranceDiff) :
    ListAdapter<Insurance, InsuranceAdapter.ViewHolder>(diff){

    private lateinit var listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_insurance, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val insurance = getItem(position)
        with(holder){
            setListener(insurance)
            binding?.setVariable(BR.insurance, insurance)
            binding?.executePendingBindings()
        }
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemInsuranceBinding>(view)

        fun setListener(insurance: Insurance) {
            binding?.root?.setOnClickListener { listener.onClick(insurance) }
        }
    }
}