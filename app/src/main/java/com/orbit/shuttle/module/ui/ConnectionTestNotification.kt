package com.orbit.shuttle.module.ui

import android.content.Context
import androidx.core.app.NotificationCompat
import com.orbit.shuttle.R
import com.orbit.shuttle.SeeWApp
import com.orbit.shuttle.ktx.Logs

class ConnectionTestNotification(val context: Context, val title: String) {
    private val channelId = "ch_test"
    private val notificationId = 1001

    fun updateNotification(progress: Int, max: Int, finished: Boolean) {
        try {
            if (finished) {
                SeeWApp.notification.cancel(notificationId)
                return
            }
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_service_active)
                .setContentTitle(title)
                .setOnlyAlertOnce(true)
                .setContentText("$progress / $max").setProgress(max, progress, false)
            SeeWApp.notification.notify(notificationId, builder.build())
        } catch (e: Exception) {
            Logs.w(e)
        }
    }
}