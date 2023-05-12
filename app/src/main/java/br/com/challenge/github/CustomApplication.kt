package br.com.challenge.github

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@CustomApplication)
            androidLogger()
            modules(AppKoinModule.module)
        }
    }
}