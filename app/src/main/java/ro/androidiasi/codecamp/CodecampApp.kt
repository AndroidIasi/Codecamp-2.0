package ro.androidiasi.codecamp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

/**
 * Created by andrei.
 */
class CodecampApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        Timber.plant(Timber.DebugTree())
    }

}