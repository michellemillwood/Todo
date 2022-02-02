package se.millwood.todo.cardlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import se.millwood.todo.data.Card
import se.millwood.todo.data.CardWithTodos
import se.millwood.todo.data.Repository
import java.util.*

class CardListViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    val cardsWithTodos: Flow<List<CardWithTodos>> = repository.cardsWithTodos

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