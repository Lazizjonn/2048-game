package uz.gita.game_2048_lazizjon.app

import android.app.Application
import uz.gita.game_2048_lazizjon.data.local.sharedPref.SharedPref
import uz.gita.game_2048_lazizjon.data.repository.impl.AppRepositoryImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
        AppRepositoryImpl.init()
    }
}