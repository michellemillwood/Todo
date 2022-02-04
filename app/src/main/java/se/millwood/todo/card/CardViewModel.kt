package se.millwood.todo.card

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import se.millwood.todo.cardlist.CardListFragment
import se.millwood.todo.data.Card
import se.millwood.todo.data.Repository
import java.util.*

class CardViewModel(context: Context, arguments: Bundle) : ViewModel() {

    private val repository = Repository(context)

    private val _cardFlow = MutableStateFlow<Card?>(null)
    val cardFlow: Flow<Card> get() = _cardFlow.filterNotNull()

    private val args: CardListFragment.CardArguments? = arguments.getParcelable(CardListFragment.CARD_ID_KEY)
    val cardId = args?.cardId

    init {
        viewModelScope.launch {
            _cardFlow.value = repository.fetchCard(UUID.fromString(cardId))
        }
    }

    fun updateCardTitle(cardTitle: String) {
        viewModelScope.launch {
            repository.updateCard(
                title = cardTitle,
                cardId = UUID.fromString(cardId)
            )
        }
    }

    fun getTodos() = repository.getTodos(UUID.fromString(cardId))

    fun setIsCompleted(
        todoId: UUID,
        isCompleted: Boolean
    ) {
        viewModelScope.launch {
            repository.setIsCompleted(
                todoId,
                isCompleted
            )
        }
    }
}
