package eu.jsonplaceholder.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.jsonplaceholder.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

}
