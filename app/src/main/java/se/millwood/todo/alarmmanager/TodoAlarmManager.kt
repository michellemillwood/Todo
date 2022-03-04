package se.millwood.todo.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import se.millwood.todo.data.Repository
import java.time.Instant
import java.util.*
import javax.inject.Inject

class TodoAlarmManager @Inject constructor(
    private val alarmManager: AlarmManager,
    private val repository: Repository,
    @ApplicationContext private val context: Context
) {

    suspend fun updateTodoAlarm(
        cardId: UUID,
        todoId: UUID,
        alarmTime: Instant?
    ) {
        repository.updateTodoAlarm(
            cardId = cardId,
            todoId = todoId,
            alarm = alarmTime
        )
        setAlarm(
            cardId = cardId,
            todoId = todoId,
            alarmTime = alarmTime
        )
    }

    fun setAlarm(
        cardId: UUID,
        todoId: UUID,
        alarmTime: Instant?
    ) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(TODO_ID, todoId.toString())
            putExtra(CARD_ID, cardId.toString())
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            todoId.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        if (alarmTime == null) {
            alarmManager.cancel(pendingIntent)
        }
        else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmTime.toEpochMilli(),
                pendingIntent
            )
        }
    }

    companion object {
        const val CARD_ID = "cardId"
        const val TODO_ID = "todoId"
    }
}
