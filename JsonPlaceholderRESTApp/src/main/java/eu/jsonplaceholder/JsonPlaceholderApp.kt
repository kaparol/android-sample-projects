package eu.jsonplaceholder

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import eu.jsonplaceholder.di.AppInjector
import javax.inject.Inject

class JsonPlaceholderApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)


    }

    override fun activityInjector() = dispatchingAndroidInjector
}