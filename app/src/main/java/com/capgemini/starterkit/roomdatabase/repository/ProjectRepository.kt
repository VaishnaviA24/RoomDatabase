package com.capgemini.starterkit.roomdatabase.repository

import androidx.lifecycle.LiveData
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import com.capgemini.starterkit.roomdatabase.room.dao.ProjectDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProjectRepository(private val projectDao: ProjectDao) {

    var allProjectsData: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    suspend fun insertProjects() {

        val projectEntities = listOf(
            ProjectEntity("1001", "ProjectOne"),
            ProjectEntity("2002", "ProjectTwo"),
            ProjectEntity("3003", "ProjectThree"),
            ProjectEntity("4004", "ProjectFour")
        )

        // Insert the static tasks using a Coroutine
        withContext(Dispatchers.IO) {
            projectDao.insertProjects(projectEntities)
        }
    }

    suspend fun insertProjectValue(projectEntity: ProjectEntity) {
        projectDao.insertProjectValue(projectEntity)
    }

    suspend fun updateProject(projectEntity: ProjectEntity) {
        projectDao.updateProject(projectEntity)
    }

    suspend fun deleteProject(projectEntity: ProjectEntity) {
        projectDao.deleteProject(projectEntity)
    }

    fun getProjectsSortedByName(): Flow<List<ProjectEntity>> {
        return projectDao.getProjectsSortedByName()
    }

    fun getProjectCount(): LiveData<Int> {
        return projectDao.getProjectCount()
    }

}