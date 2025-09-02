package com.cursosant.insurance.notificationsModule.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.insurance.BR
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.Notification
import com.cursosant.insurance.databinding.ItemNotificationBinding
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
class NotificationAdapter @Inject constructor(diff: NotificationDiff) :
    ListAdapter<Notification, RecyclerView.ViewHolder>(diff){

        private lateinit var listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val notification = getItem(position)
        with(holder as ViewHolder){
            setListener(notification)
            binding?.let {
                it.setVariable(BR.notification, notification)
                it.executePendingBindings()
            }
        }
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = DataBindingUtil.bind<ItemNotificationBinding>(view)

        fun setListener(notification: Notification){
            binding?.root?.setOnClickListener { listener.onClick(notification) }
        }
    }
}