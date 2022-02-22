package se.millwood.todo.cardlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import se.millwood.todo.settings.DataStoreManager
import se.millwood.todo.data.Card
import se.millwood.todo.data.CardWithTodos
import se.millwood.todo.data.Repository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStore: DataStoreManager
) : ViewModel() {

    fun getCardsWithTodos(): Flow<List<CardWithTodos>> = dataStore.sortOrder.flatMapLatest {
        repository.getCardsWithTodos(it)
    }

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