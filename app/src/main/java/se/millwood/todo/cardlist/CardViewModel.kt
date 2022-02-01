package se.millwood.todo.cardlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import se.millwood.todo.data.Repository
import java.util.*

class CardViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    val cards: Flow<List<Card>> = repository.cards

    fun addCard(
        title: String,
        cardId: UUID
    ) = viewModelScope.launch {
        repository.addCard(
            Card(title, cardId)
        )
    }



}