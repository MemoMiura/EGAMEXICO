package com.cursosant.insurance.policiesModule.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Policy
import com.cursosant.insurance.common.utils.DateUtils
import com.cursosant.insurance.databinding.ItemPolicyBinding
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.policiesModule.view.adapters
 * Created by Alain Nicolás Tello on 02/06/23 at 12:24
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
class PolicyAdapter @Inject constructor(diff: PolicyDiff, private val utils: DateUtils) :
    ListAdapter<Policy, RecyclerView.ViewHolder>(diff){
    private lateinit var listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_policy, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val policy = getItem(position)
        with(holder as ViewHolder){
            setListener(policy)
            binding?.let {
                it.setVariable(BR.policy, policy)
                it.setVariable(BR.utils, utils)
                it.executePendingBindings()
            }
        }
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemPolicyBinding>(view)

        fun setListener(policy: Policy) {
            binding?.root?.setOnClickListener { listener.onClick(policy) }
        }
    }
}