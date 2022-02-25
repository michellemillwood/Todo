package se.millwood.todo.card

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import se.millwood.todo.cardlist.CardListFragment
import se.millwood.todo.data.Repository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args: CardListFragment.CardArguments? =
        savedStateHandle.get(
            CardListFragment.CARD_ID_KEY
        )

    val cardId = args?.cardId

    val image: Flow<Uri?> = repository.getCardImage(UUID.fromString(cardId))

    suspend fun getCardTitle() = repository.getCardTitle(UUID.fromString(cardId))

    fun getTodos() = repository.getTodos(UUID.fromString(cardId))

    fun updateCardTitle(cardTitle: String) {
        viewModelScope.launch {
            repository.updateCardTitle(
                title = cardTitle,
                cardId = UUID.fromString(cardId),
            )
        }
    }


    fun setTodoAlarm(todoId: UUID, alarmDateTime: Calendar) {
        viewModelScope.launch {
            repository.setTodoAlarm(
                todoId = todoId,
                cardId = UUID.fromString(cardId),
                alarmTime = alarmDateTime.toInstant()
            )
        }
    }

    fun updateCardImage(imageUri: Uri) {
        viewModelScope.launch {
            repository.updateCardImage(
                UUID.fromString(cardId),
                imageUri
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
