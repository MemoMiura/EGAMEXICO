package com.cursosant.insurance.common.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.cursosant.insurance.R
import com.cursosant.insurance.common.utils.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.common.fcm
 * Created by Alain Nicolás Tello on 27/11/23 at 15
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
@AndroidEntryPoint
open class BaseFCMService : FirebaseMessagingService() {
    private var iconRes: Int = R.drawable.ic_stat_name
    private var colorRes: Int = R.color.color_primary
    private lateinit var intent: Intent

    @Inject
    lateinit var dataStore: DataStore<Preferences>
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        registerNewTokenLocal(newToken)
    }

    private fun registerNewTokenLocal(newToken: String){
        Log.i("CursosANTLog", newToken)
    }

    protected fun setValues(iconRes: Int, colorRes: Int, intent: Intent){
        this.iconRes = iconRes
        this.colorRes = colorRes
        this.intent = intent
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        processNotification(remoteMessage)
    }

    protected fun processNotification(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            val title = it.title
            val imgUrl = it.imageUrl

            if (imgUrl == null){
                //sendNotificationImg(it)
                sendNotificationTxt(it.title, it.body)
            } else {
                if (remoteMessage.data.isNotEmpty()) {
                    val body = remoteMessage.data["message"]
                    val imageUri = remoteMessage.data["image"]

                    Glide.with(applicationContext)
                        .asBitmap()
                        .load(imageUri)//.load(imgUrl)
                        .into(object : CustomTarget<Bitmap?>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap?>?
                            ) {
                                sendNotificationImg(title, body, resource)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {}
                        })
                }
            }
        }
    }

    private fun sendNotificationImg(title: String?, body: String?, bitmap: Bitmap? = null){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(Constants.ARG_THERE_IS_NOTIFY, Constants.ARG_THERE_IS_NOTIFY)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.notification_channel_id_default)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(iconRes)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setColor(ContextCompat.getColor(this, colorRes))
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(body))

        bitmap?.let {
            notificationBuilder
                .setLargeIcon(bitmap)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null as Bitmap?))
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,
                getString(R.string.notification_channel_name_default),
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())

        saveMainNotifyIcon()
    }

    private fun sendNotificationTxt(title: String?, body: String?){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(Constants.ARG_THERE_IS_NOTIFY, Constants.ARG_THERE_IS_NOTIFY)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.notification_channel_id_default)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(iconRes)
            .setContentTitle(title)//data["title"])
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setColor(ContextCompat.getColor(this, colorRes))
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(body))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,
                getString(R.string.notification_channel_name_default),
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())

        saveMainNotifyIcon()
    }

    private fun saveMainNotifyIcon() {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[Constants.K_THERE_IS_NOTIFY] = true
            }
        }
    }
}