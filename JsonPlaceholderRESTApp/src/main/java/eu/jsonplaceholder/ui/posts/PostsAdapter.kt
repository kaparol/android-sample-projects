package eu.jsonplaceholder.ui.posts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import eu.jsonplaceholder.model.Post


//TODO AsyncDifferConfig
//TODO private val dataBindingComponent: DataBindingComponent,
//TODO
class PostsAdapter(
    private val clickCallback: ((Post, View) -> Unit)?
) : ListAdapter<Post,PostVieHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVieHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: PostVieHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class PostVieHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {

}