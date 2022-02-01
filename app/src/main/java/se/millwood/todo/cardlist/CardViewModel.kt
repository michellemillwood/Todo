package se.millwood.todo.cardlist

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import se.millwood.todo.data.Repository

class CardViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    val cards: Flow<List<Card>> = repository.cards


}