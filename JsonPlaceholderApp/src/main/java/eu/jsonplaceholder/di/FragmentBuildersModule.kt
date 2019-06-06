package eu.jsonplaceholder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.jsonplaceholder.ui.details.PostDetailsFragment
import eu.jsonplaceholder.ui.posts.PostsFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributePostsFragment(): PostsFragment

    @ContributesAndroidInjector
    abstract fun contributePostDetailsFragment(): PostDetailsFragment
}
