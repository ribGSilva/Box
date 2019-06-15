package com.example.boxcontroller.domain.pushnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.boxcontroller.R
import com.example.boxcontroller.view.activity.MainActivity

object NotificationCreator {
    private var notificationManager: NotificationManager? = null

    private val sVIBRATION = longArrayOf(300, 400, 500, 400, 300)

    private const val sCHANNEL_ID = "box_notification"
    private const val sCHANNEL_NAME = "Box Notification"
    private const val sCHANNEL_DESCRIPTION = "Notificações sobre eventos da caixa"

    fun create(context: Context, title: String, body: String, notificationId: Int) {
        if (notificationManager == null)
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = notificationManager?.getNotificationChannel(sCHANNEL_ID)

            if (channel == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH

                channel = NotificationChannel(sCHANNEL_ID, sCHANNEL_NAME, importance).apply {
                    description = sCHANNEL_DESCRIPTION
                    enableVibration(true)
                    enableLights(true)
                    vibrationPattern = sVIBRATION
                }

                notificationManager?.createNotificationChannel(channel)
            }
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, sCHANNEL_ID)
            .setContentText(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(body)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)
            .setTicker(title)
            .setVibrate(sVIBRATION)
            .setOnlyAlertOnce(false)
            .setOngoing(true)
            .setStyle(NotificationCompat
                    .BigTextStyle()
                    .bigText(body))

        val notificationApp = builder.build()

        notificationManager?.notify(notificationId, notificationApp)

    }

    fun cancel(context: Context, notificationId: Int) {
        if (notificationManager == null)
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager?.cancel(notificationId)
    }
}