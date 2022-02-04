package se.millwood.todo.cardlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import se.millwood.todo.data.Card
import se.millwood.todo.data.CardWithTodos
import se.millwood.todo.data.Repository
import se.millwood.todo.settings.SettingsFragment
import se.millwood.todo.settings.SettingsFragment.Companion.SORT_ORDER_KEY
import java.util.*

class CardListViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    private val sharedPrefs = context.getSharedPreferences(
        SettingsFragment.SETTINGS,
        Context.MODE_PRIVATE
    )
    private val sortOrder = sharedPrefs.getString(
        SORT_ORDER_KEY,
        SettingsFragment.Companion.SortOrder.ALPHABETICAL.name
    )

    val cardsWithTodos: Flow<List<CardWithTodos>> = repository.getCardWithTodos(
        SettingsFragment.Companion.SortOrder.valueOf(sortOrder!!)
    )


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