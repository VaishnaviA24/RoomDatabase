package com.capgemini.starterkit.roomdatabase.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capgemini.starterkit.roomdatabase.room.dao.ProjectDao
import com.capgemini.starterkit.roomdatabase.room.database.MyDatabase
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectRepositoryTest {

    private lateinit var database: MyDatabase
    private lateinit var projectDao: ProjectDao
    private lateinit var projectRepository: ProjectRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MyDatabase::class.java).build()
        projectDao = database.projectDao()
        projectRepository = ProjectRepository(projectDao)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertProjectvalues()= runBlocking {

    }

    @Test
    fun updateProjectById() {
    }

    @Test
    fun deleteProjectById() {
    }
}