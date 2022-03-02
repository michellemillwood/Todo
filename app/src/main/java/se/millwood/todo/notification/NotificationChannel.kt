package se.millwood.todo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import se.millwood.todo.BuildConfig

object NotificationChannel {

    const val CHANNEL_ID = BuildConfig.APPLICATION_ID
    private const val CHANNEL_NAME = "todo_push"
    private const val DESCRIPTION = "todo alarm notifications"

    fun createNotificationChannel(context: Context) {

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = DESCRIPTION
            setShowBadge(false)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}