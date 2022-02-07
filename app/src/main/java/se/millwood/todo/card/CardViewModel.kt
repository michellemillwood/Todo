package se.millwood.todo.card

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import se.millwood.todo.cardlist.CardListFragment
import se.millwood.todo.data.Repository
import java.util.*

class CardViewModel(context: Context, arguments: Bundle) : ViewModel() {

    private val repository = Repository(context)

    private val args: CardListFragment.CardArguments? =
        arguments.getParcelable(
            CardListFragment.CARD_ID_KEY
        )

    val cardId = args?.cardId

    suspend fun getCardTitle() = repository.getCardTitle(UUID.fromString(cardId))

    fun getTodos() = repository.getTodos(UUID.fromString(cardId))

    fun updateCardTitle(cardTitle: String) {
        viewModelScope.launch {
            repository.updateCardTitle(
                title = cardTitle,
                cardId = UUID.fromString(cardId)
            )
        }
    }

    fun setIsCompleted(
        todoId: UUID,
        isCompleted: Boolean
    ) {
        viewModelScope.launch {
            repository.setIsCompleted(
                UUID.fromString(cardId),
                todoId,
                isCompleted
            )
        }
    }
}
