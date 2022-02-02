package se.millwood.todo

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import se.millwood.todo.card.CardViewModel
import se.millwood.todo.card.TodoDeleteViewModel
import se.millwood.todo.card.TodoEditViewModel
import se.millwood.todo.cardlist.CardListViewModel

class ViewModelFactory(
    private val context: Context,
    private val args: Bundle?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(CardListViewModel::class.java) -> {
                CardListViewModel(context) as T
            }
            modelClass.isAssignableFrom(CardViewModel::class.java) -> {
                CardViewModel(context, args ?: Bundle.EMPTY) as T
            }
            modelClass.isAssignableFrom(TodoEditViewModel::class.java) -> {
                TodoEditViewModel(context, args ?: Bundle.EMPTY) as T
            }
            modelClass.isAssignableFrom(TodoDeleteViewModel::class.java) -> {
                TodoDeleteViewModel(context, args ?: Bundle.EMPTY) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
