package com.capgemini.starterkit.roomdatabase.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.dao.ProjectDao
import com.capgemini.starterkit.roomdatabase.room.dao.EmployeeDao

@Database(entities = [EmployeeEntity::class, ProjectEntity::class], version = 2, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun dataEntryDao(): EmployeeDao
    abstract fun projectDao(): ProjectDao

    companion object {

        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: MyDatabase? = null

        /** Note: When you modify the database schema,
        you'll need to update the version number and define a migration strategy.*/

        fun getDatabase(ctx: Context): MyDatabase {
            return when (val temp = INSTANCE) {
                null -> synchronized(this) {
                    Room.databaseBuilder(
                        ctx.applicationContext, MyDatabase::class.java,
                        "DataBase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

                else -> temp
            }
        }
    }
}