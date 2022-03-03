package se.millwood.todo.uitools

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeHandler(val onSwiped: (position: Int) -> Unit) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        swipeDir: Int
    ) = onSwiped(viewHolder.adapterPosition)

}