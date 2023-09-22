package com.capgemini.starterkit.roomdatabase.repository

import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import com.capgemini.starterkit.roomdatabase.room.dao.ProjectDao
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository(private val projectDao: ProjectDao) {

    suspend fun insertProjectvalues() {

        val projectEntities = listOf(
            ProjectEntity(1001, "ProjectOne"),
            ProjectEntity(2002, "ProjectTwo"),
            ProjectEntity(3003, "ProjectThree"),
            ProjectEntity(4004, "ProjectFour")
        )

        // Insert the static tasks using a Coroutine
        withContext(Dispatchers.IO) {
            projectDao.insertProject(projectEntities)
        }
    }

    suspend fun updateProjectById(projectEntity: ProjectEntity) {
        projectDao.updateProject(projectEntity)
    }

    suspend fun deleteProjectById(projectEntity: ProjectEntity) {
        projectDao.deleteProject(projectEntity)
    }
}