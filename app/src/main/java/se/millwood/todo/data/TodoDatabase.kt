package se.millwood.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class, CardEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
    abstract fun cardDao(): CardDao

}