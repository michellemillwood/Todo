package se.millwood.todo.di

import android.app.AlarmManager
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import se.millwood.todo.data.TodoDatabase
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_database"
        ).build()

    @Provides
    fun provideCalendar(): Calendar = Calendar.getInstance()

    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}
