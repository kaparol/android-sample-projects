package eu.jsonplaceholder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import eu.jsonplaceholder.ui.posts.PostsViewModel
import eu.jsonplaceholder.vm.InjectableViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    //FIXME
    @ViewModelKey(PostsViewModel::class)
    abstract fun bindPostsViewModel(postsViewModel: PostsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: InjectableViewModelFactory): ViewModelProvider.Factory
}
