package eu.jsonplaceholder.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import eu.jsonplaceholder.R
import eu.jsonplaceholder.databinding.CommentItemBinding
import eu.jsonplaceholder.model.Comment


class CommentsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val clickCallback: ((Comment) -> Unit)?
) : ListAdapter<Comment, CommentsAdapter.CommentsViewHolder>(CommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = createBinding(parent)

        binding.root.setOnClickListener {
            binding.comment?.let {
                clickCallback?.invoke(it)
            }
        }
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.binding.comment = getItem(position)
        holder.binding.executePendingBindings()
    }


    private fun createBinding(parent: ViewGroup): CommentItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.comment_item,
            parent,
            false,
            dataBindingComponent
        )
    }


    inner class CommentsViewHolder(val binding: CommentItemBinding): RecyclerView.ViewHolder(binding.root)
}
