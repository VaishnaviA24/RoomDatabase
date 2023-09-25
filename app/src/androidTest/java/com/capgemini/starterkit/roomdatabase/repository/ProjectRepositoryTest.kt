package com.capgemini.starterkit.roomdatabase.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capgemini.starterkit.roomdatabase.room.dao.ProjectDao
import com.capgemini.starterkit.roomdatabase.room.database.MyDatabase
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

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
    fun insertProjects() = runBlocking {
        projectRepository.insertProjects()

        val projectsFromDb = projectDao.getAllProjects().firstOrNull()
        assertNotNull(projectsFromDb)
        Log.d("ProjectRepositoryTest", "insertProjects - Number of projects: ${projectsFromDb!!.size}")
        assertEquals(4, projectsFromDb!!.size)
    }

    @Test
    fun insertAndGetProjectValue() = runBlocking {
        val project =
            ProjectEntity(
                projectId = 1,
                projectName = "TestProj"
            )
        projectRepository.insertProjectValue(project)

        val allData = projectRepository.allProjectsData.first()
        Log.d("ProjectRepositoryTest", "insertAndGetProjectValue - List size: ${allData.size}")
        assertEquals(1, allData.size)
        assertEquals(project, allData[0])
    }

    @Test
    fun updateProject() = runBlocking {
        val project = ProjectEntity(1, "TestProject")
        projectRepository.insertProjectValue(project)

        val updatedProject = ProjectEntity(1, "UpdatedProject")
        projectRepository.updateProject(updatedProject)

        val projectsFromDb = projectRepository.allProjectsData.firstOrNull()
        Log.d("ProjectRepositoryTest", "updateProject - Updated project: ${projectsFromDb!![0]}")
        assertNotNull(projectsFromDb)
                assertEquals(1, projectsFromDb!!.size)
        assertEquals(updatedProject, projectsFromDb[0])
    }

    @Test
    fun deleteProject() = runBlocking {
        val project = ProjectEntity(1, "TestProject")
        projectRepository.insertProjectValue(project)

        projectRepository.deleteProject(project)

        val projectsFromDb = projectRepository.allProjectsData.firstOrNull()
        Log.d("ProjectRepositoryTest", "deleteProject - Table size: ${projectsFromDb!!.size}")
        assertNotNull(projectsFromDb)
        assertTrue(projectsFromDb.isNullOrEmpty())
    }
}