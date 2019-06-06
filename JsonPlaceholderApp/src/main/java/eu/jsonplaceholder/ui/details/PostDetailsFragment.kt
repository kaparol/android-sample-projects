package eu.jsonplaceholder.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import eu.jsonplaceholder.R
import eu.jsonplaceholder.binding.FragmentDataBindingComponent
import eu.jsonplaceholder.databinding.PostDetailsFragmentBinding
import eu.jsonplaceholder.di.Injectable
import eu.jsonplaceholder.ui.LoadCallback
import javax.inject.Inject

class PostDetailsFragment : Fragment(), Injectable {

    companion object{
        const val POST_ID_KEY = "postId"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var postDetailsViewModel: PostDetailsViewModel

    private lateinit var adapter: CommentsAdapter

    private lateinit var binding: PostDetailsFragmentBinding

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<PostDetailsFragmentBinding>(
            inflater,
            R.layout.post_details_fragment,
            container,
            false,
            dataBindingComponent
        )

        dataBinding.callback = object : LoadCallback{
            override fun load() {
                var postId = arguments?.getLong(POST_ID_KEY)
                postDetailsViewModel.postId.value = postId
            }
        }

        binding = dataBinding

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PostDetailsViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = CommentsAdapter(dataBindingComponent = dataBindingComponent) { post ->
           //do nothing, not supported yet
        }
        this.adapter = adapter
        binding.commentsList.layoutManager = LinearLayoutManager(view.context)
        binding.commentsList.adapter = adapter


        binding.user = postDetailsViewModel.user
        binding.post = postDetailsViewModel.post
        binding.comments = postDetailsViewModel.comments

        var postId = arguments?.getLong(POST_ID_KEY)
        postDetailsViewModel.postId.value = postId
        initCommentsList(postDetailsViewModel)
    }

    private fun initCommentsList(viewModel: PostDetailsViewModel) {
        viewModel.comments.observe(viewLifecycleOwner, Observer { commentsList ->
            commentsList?.let {
                adapter.submitList(it.data)
            } ?: adapter.submitList(emptyList())
        })

    }
}




