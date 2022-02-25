package se.millwood.todo.data

import androidx.room.TypeConverter
import java.time.Instant

class Converters {

    @TypeConverter
    fun decode(databaseValue: Long?) : Instant? {
        return databaseValue?.let { Instant.ofEpochMilli(it) }
    }

    @TypeConverter
    fun encode(timeStamp: Instant?): Long? {
        return timeStamp?.toEpochMilli()
    }
}