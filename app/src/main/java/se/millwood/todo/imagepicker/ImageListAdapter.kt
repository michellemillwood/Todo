package se.millwood.todo.imagepicker

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.request.ImageRequest
import se.millwood.todo.databinding.ItemImageBinding

class ImageListAdapter : ListAdapter<Uri, ImageListAdapter.ImageViewHolder>(DiffCallback) {

    class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uri = getItem(position)

        Coil.enqueue(
            ImageRequest.Builder(holder.itemView.context)
                .data(uri)
                .target(holder.binding.itemImage)
                .build()
        )

    }

    object DiffCallback : DiffUtil.ItemCallback<Uri>() {

        override fun areItemsTheSame(
            oldItem: Uri,
            newItem: Uri
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Uri,
            newItem: Uri
        ) = oldItem == newItem
    }
}