package eu.jsonplaceholder.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import eu.jsonplaceholder.model.Comment
import eu.jsonplaceholder.model.LoadStateObject
import eu.jsonplaceholder.model.Post
import eu.jsonplaceholder.model.User
import eu.jsonplaceholder.repository.PostsRepository
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(private val repository: PostsRepository): ViewModel() {

    val postId = MutableLiveData<Long>()

    val post : LiveData<LoadStateObject<Post>> = Transformations.switchMap(postId){ postId->
        repository.loadPost(postId)
    }

    val comments : LiveData<LoadStateObject<List<Comment>>> = Transformations.switchMap(postId){ postId->
        repository.loadCommentsByPost(postId)
    }

    val user : LiveData<LoadStateObject<User>> = Transformations.switchMap(post){ post ->
        post.data?.let {
            repository.loadUser(it.userId)
        }
    }

    val users : LiveData<LoadStateObject<List<User>>> = Transformations.switchMap(postId){
        repository.loadUsers()
    }
}