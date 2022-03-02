package se.millwood.todo.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.card.CardFragment
import se.millwood.todo.data.Repository
import se.millwood.todo.notification.NotificationChannel
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var repository: Repository


    override fun onReceive(context: Context, intent: Intent) {

        val cardId = intent.getStringExtra(TodoAlarmManager.CARD_ID) ?: return
        val todoId = intent.getStringExtra(TodoAlarmManager.TODO_ID) ?: return

        CoroutineScope(Dispatchers.IO).launch {
            repository.updateTodoAlarm(UUID.fromString(cardId), UUID.fromString(todoId), null)
        }

        val bundle = bundleOf(
            CardFragment.TODO_EDIT_ARGUMENTS to CardFragment.TodoEditArguments(
                cardId = cardId,
                todoId = todoId
            )
        )

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.todoEditDialogFragment)
            .setArguments(bundle)
            .createPendingIntent()

        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            // reset all alarms from db
        }
        else {
            CoroutineScope(Dispatchers.IO).launch {
                val todoTitle = repository.getTodoTitle(UUID.fromString(todoId))
                val builder = NotificationCompat.Builder(context, NotificationChannel.CHANNEL_ID)
                    .setContentTitle(todoTitle)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)

                val notification = builder.build()

                NotificationManagerCompat.from(context).notify(todoId.hashCode(), notification)

            }
        }
    }


}