package com.capgemini.starterkit.roomdatabase.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Project::class], version = 1, exportSchema = false)
abstract class MainInfoDatabase : RoomDatabase() {

    abstract fun dataEntryDao(): UserDao
    abstract fun projectDao(): ProjectDao

    companion object {

        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: MainInfoDatabase? = null

        /** Note: When you modify the database schema,
        you'll need to update the version number and define a migration strategy.*/

        fun getDatabase(ctx: Context): MainInfoDatabase {
            return when (val temp = INSTANCE) {
                null -> synchronized(this) {
                    Room.databaseBuilder(
                        ctx.applicationContext, MainInfoDatabase::class.java,
                        "user_info"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

                else -> temp
            }
        }
    }
}