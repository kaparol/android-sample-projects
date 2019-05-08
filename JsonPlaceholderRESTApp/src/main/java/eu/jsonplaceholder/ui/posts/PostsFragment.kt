package eu.jsonplaceholder.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import eu.jsonplaceholder.R
import eu.jsonplaceholder.databinding.PostsFragmentBinding
import eu.jsonplaceholder.di.Injectable
import eu.jsonplaceholder.ui.LoadCallback
import javax.inject.Inject

class PostsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var postsViewModel: PostsViewModel

    lateinit var adapter: PostsAdapter

    private lateinit var binding: PostsFragmentBinding

    private fun initPostsList(viewModel: PostsViewModel) {
        viewModel.posts.observe(viewLifecycleOwner, Observer { postsList ->
            postsList?.let {
                adapter.submitList(it.data)
            }?: adapter.submitList(emptyList())
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<PostsFragmentBinding>(
            inflater,
            R.layout.posts_fragment,
            container,
            false
        )
        dataBinding.loadCallback = object : LoadCallback {
            override fun load() {
                postsViewModel.load()
            }
        }

        binding = dataBinding
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PostsViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = PostsAdapter { _, _ ->

        }
        this.adapter = adapter
        binding.postsList.layoutManager = LinearLayoutManager(view.context)
        binding.postsList.adapter = adapter
        postponeEnterTransition()
        binding.postsList.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        initPostsList(postsViewModel)
    }
}