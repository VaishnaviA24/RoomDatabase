package com.capgemini.starterkit.roomdatabase.repository

import com.capgemini.starterkit.roomdatabase.room.Project
import com.capgemini.starterkit.roomdatabase.room.ProjectDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository(private val projectDao: ProjectDao) {

    suspend fun insertProjectvalues() {

        val projects = listOf(
            Project(1001,"ProjectOne"),
            Project(2002,"ProjectTwo"),
            Project(3003,"ProjectThree"),
            Project(4004,"ProjectFour")
        )

        // Insert the static tasks using a Coroutine
        withContext(Dispatchers.IO) {
            projectDao.insertProject(projects)
        }
    }

    fun getUserWithProjectValues(){
        projectDao.getUserWithProject()
    }
}