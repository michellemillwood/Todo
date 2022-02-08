package se.millwood.todo.cardlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.data.Card
import se.millwood.todo.data.CardWithTodos
import se.millwood.todo.data.Repository
import se.millwood.todo.dataStore
import se.millwood.todo.settings.SettingsFragment
import java.util.*

class CardListViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    private var sortOrder = SettingsFragment.Companion.SortOrder.LAST_EDITED

    init {
        viewModelScope.launch {
            context.dataStore.data.collect { preferences ->
                preferences[SettingsFragment.sortOrderKey]?.let {
                    sortOrder = SettingsFragment.Companion.SortOrder.valueOf(it)
                }
            }
        }
    }

    fun getCardsWithTodos(): Flow<List<CardWithTodos>> = repository.getCardsWithTodos(sortOrder)

    fun deleteCardWithTodos(
        cardWithTodos: CardWithTodos
    ) {
        viewModelScope.launch {
            repository.deleteCardWithTodos(cardWithTodos.card.cardId)
        }
    }

    fun createCard(): UUID {
        val card = Card()
        viewModelScope.launch {
            repository.addCard(card)
        }
        return card.cardId
    }
}