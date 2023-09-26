package com.capgemini.starterkit.roomdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.starterkit.roomdatabase.repository.ProjectRepository
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val projectRepository: ProjectRepository
) : ViewModel() {

    val getAllProjData: LiveData<List<ProjectEntity>> = projectRepository.allProjectsData.asLiveData()

    fun insertProjects() {
        viewModelScope.launch {
            projectRepository.insertProjects()
        }
    }

    fun insertProjectValue(projectEntity: ProjectEntity) {
        viewModelScope.launch {
            projectRepository.insertProjectValue(projectEntity)
        }
    }

    fun updateById(projectEntity: ProjectEntity) {
        viewModelScope.launch {
            projectRepository.updateProject(projectEntity)
        }
    }

    fun deleteById(projectEntity: ProjectEntity) {
        viewModelScope.launch {
            projectRepository.deleteProject(projectEntity)
        }
    }

}