package com.capgemini.starterkit.roomdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.starterkit.roomdatabase.repository.ProjectRepository
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val project_repository: ProjectRepository
) : ViewModel() {

    val getAllProjData: LiveData<List<ProjectEntity>> = project_repository.allProjectsData.asLiveData()

    fun insertProjects() {
        viewModelScope.launch {
            project_repository.insertProjects()
        }
    }

    fun insertProjectValue(projectEntity: ProjectEntity) {
        viewModelScope.launch {
            project_repository.insertProjectValue(projectEntity)
        }
    }

    fun updateById(projectEntity: ProjectEntity) {
        viewModelScope.launch {
            project_repository.updateProject(projectEntity)
        }
    }

    fun deleteById(projectEntity: ProjectEntity) {
        viewModelScope.launch {
            project_repository.deleteProject(projectEntity)
        }
    }

}