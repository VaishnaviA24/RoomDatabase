package com.capgemini.starterkit.roomdatabase

import android.app.Application
import com.capgemini.starterkit.roomdatabase.repository.MainInfoRepository
import com.capgemini.starterkit.roomdatabase.room.MainInfoDatabase

class MainApplication : Application(){
    companion object {
        @Volatile
        private var INSTANCE: MainApplication? = null

        fun getApplicationInstance() : MainApplication? {
            return INSTANCE
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    /** By using lazy the database and the repository are only created when they're needed
     * rather than when the application starts
     **/
    private val database by lazy { MainInfoDatabase.getDatabase(this) }
    val repository by lazy { MainInfoRepository(database.dataEntryDao()) }
}