package br.com.bootcampinter.application

import android.app.Application
import br.com.bootcampinter.database.HelperDB

class ContactApplication: Application() {

    var helperDB: HelperDB? = null
        private set

    override fun onCreate() {
        super.onCreate()
        helperDB = HelperDB(this)
    }
}