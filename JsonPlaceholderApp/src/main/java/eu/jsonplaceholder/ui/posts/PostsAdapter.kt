package eu.jsonplaceholder.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import eu.jsonplaceholder.R
import eu.jsonplaceholder.databinding.PostItemBinding
import eu.jsonplaceholder.model.Post


class PostsAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val clickCallback: ((Post) -> Unit)?
) : ListAdapter<Post, PostsAdapter.PostVieHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVieHolder {
        val binding = createBinding(parent)

        binding.root.setOnClickListener {
            binding.post?.let {
                clickCallback?.invoke(it)
            }
        }
        return PostVieHolder(binding)
    }

    override fun onBindViewHolder(holder: PostVieHolder, position: Int) {
        holder.binding.post = getItem(position)
        holder.binding.executePendingBindings()
    }


    private fun createBinding(parent: ViewGroup): PostItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.post_item,
            parent,
            false,
            dataBindingComponent
        )
    }


    inner class PostVieHolder(val binding: PostItemBinding): RecyclerView.ViewHolder(binding.root)
}
