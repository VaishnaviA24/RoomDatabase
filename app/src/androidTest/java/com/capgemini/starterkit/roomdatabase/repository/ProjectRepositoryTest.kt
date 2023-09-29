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
        assertEquals(4, projectsFromDb.size)
    }

    @Test
    fun insertAndGetProjectValue() = runBlocking {
        val project =
            ProjectEntity(
                projectId = "1",
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
        val project = ProjectEntity("1", "TestProject")
        projectRepository.insertProjectValue(project)

        val updatedProject = ProjectEntity("1", "UpdatedProject")
        projectRepository.updateProject(updatedProject)

        val projectsFromDb = projectRepository.allProjectsData.firstOrNull()
        Log.d("ProjectRepositoryTest", "updateProject - Updated project: ${projectsFromDb!![0]}")
        assertNotNull(projectsFromDb)
                assertEquals(1, projectsFromDb.size)
        assertEquals(updatedProject, projectsFromDb[0])
    }

    @Test
    fun deleteProject() = runBlocking {
        val project = ProjectEntity("1", "TestProject")
        projectRepository.insertProjectValue(project)

        projectRepository.deleteProject(project)

        val projectsFromDb = projectRepository.allProjectsData.firstOrNull()
        Log.d("ProjectRepositoryTest", "deleteProject - Table size: ${projectsFromDb!!.size}")
        assertNotNull(projectsFromDb)
        assertTrue(projectsFromDb.isEmpty())
    }

    @Test
    fun getProjectsSortedByName() = runBlocking {
        // Insert projects into the database
        val project1 = ProjectEntity("1", "Project One")
        val project2 = ProjectEntity("2", "Project Two")
        val project3 = ProjectEntity("3", "Project Three")
        val project4 = ProjectEntity("4", "Project Four")
        val unsortedProjects = listOf(project2, project4, project1, project3)
        projectDao.insertProjects(unsortedProjects)

        // Retrieve sorted projects
        val sortedProjects = projectRepository.getProjectsSortedByName().firstOrNull()
        Log.d("ProjectRepositoryTest", "getProjectsSortedByName - Sorted projects: $sortedProjects")

        assertNotNull(sortedProjects)
        assertEquals(4, sortedProjects!!.size)

        // Check if the projects are sorted by name in ascending order
        assertEquals("Project Four", sortedProjects[0].projectName)
        assertEquals("Project One", sortedProjects[1].projectName)
        assertEquals("Project Three", sortedProjects[2].projectName)
        assertEquals("Project Two", sortedProjects[3].projectName)
    }
}