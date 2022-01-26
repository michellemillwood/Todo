package se.millwood.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: TodoDatabase

        fun getDatabase(context: Context): TodoDatabase {
            if (!this::INSTANCE.isInitialized) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        TodoDatabase::class.java,
                        "todo_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

}