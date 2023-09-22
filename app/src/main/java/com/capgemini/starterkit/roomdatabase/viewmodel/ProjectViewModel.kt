package com.capgemini.starterkit.roomdatabase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capgemini.starterkit.roomdatabase.repository.ProjectRepository
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val project_repository: ProjectRepository
) : ViewModel() {

    fun insertValues() {
        viewModelScope.launch {
            project_repository.insertProjectvalues()
        }
    }

    fun updateById(projectEntity: ProjectEntity) {
        viewModelScope.launch {
            project_repository.updateProjectById(projectEntity)
        }
    }

    fun deleteById(projectEntity: ProjectEntity) {
        viewModelScope.launch {
            project_repository.deleteProjectById(projectEntity)
        }
    }

}