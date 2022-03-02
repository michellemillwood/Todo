package se.millwood.todo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import se.millwood.todo.notification.NotificationChannel

@HiltAndroidApp
class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationChannel.createNotificationChannel(this)
    }
}
