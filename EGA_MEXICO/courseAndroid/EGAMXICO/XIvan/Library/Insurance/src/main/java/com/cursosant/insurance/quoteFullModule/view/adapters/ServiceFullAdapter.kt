package com.cursosant.insurance.quoteFullModule.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.ServiceResponse
import com.cursosant.insurance.databinding.ItemQuoteResultBinding
import com.cursosant.insurance.quoteResultModule.view.adapters.OnClickListener
import javax.inject.Inject

/****
 * Project: Insurance
 * From: com.cursosant.insurance.quoteResultModule.view
 * Created by Alain Nicolás Tello on 22/06/23 at 14:35
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
class ServiceFullAdapter @Inject constructor() :
    RecyclerView.Adapter<ServiceFullAdapter.ViewHolder>() {
    //private lateinit var services: MutableList<ServiceResponse>
    private val services: MutableList<ServiceResponse> by lazy { mutableListOf() }
    private lateinit var listener: OnClickListener
    private var type: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_quote_result, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //type = "Contado"
        val service = services[position].also { it.type = type }
        with(holder){
            setListener(service)
            binding?.let {
                /*val quote = Quote(service.id, service.service?.name, service.quote_pay_frecuency)
                it.setVariable(BR.quote, quote)*/
                it.setVariable(BR.service, service)
                it.executePendingBindings()
            }
        }
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setType(type: String) {
        this.type = type
        notifyDataSetChanged()
    }

    fun add(service: ServiceResponse) {
        if (!services.contains(service)) {
            services.add(service)
            notifyItemInserted(services.size - 1)
        } else {
            val index = services.indexOf(service)
            services[index] = service
            notifyItemChanged(index)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        services.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = services.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemQuoteResultBinding>(view)

        fun setListener(service: ServiceResponse) {
            binding?.root?.setOnClickListener { listener.onClick(service) }
        }
    }
}