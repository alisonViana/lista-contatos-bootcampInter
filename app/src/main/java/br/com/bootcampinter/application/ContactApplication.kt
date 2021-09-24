package br.com.bootcampinter.application

import android.app.Application
import br.com.bootcampinter.database.HelperDB

class ContactApplication: Application() {

    var helperDB: HelperDB? = null
        private set

    companion object{
        lateinit var instance: ContactApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}