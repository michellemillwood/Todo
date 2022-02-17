package se.millwood.todo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val sortOrderKey = stringPreferencesKey("sort_order")

    val sortOrder: Flow<SortOrder> = context.dataStore.data.map { preferences ->
        SortOrder.valueOf(preferences[sortOrderKey] ?:
        SortOrder.LAST_EDITED.name)
    }

    suspend fun updateSortKey(
        sortOrder: SortOrder
    ) = context.dataStore.edit { preferences ->
        preferences[sortOrderKey] = sortOrder.name
    }

}

enum class SortOrder {
    ALPHABETICAL,
    LAST_EDITED,
    TODO_LIST_SIZE
}