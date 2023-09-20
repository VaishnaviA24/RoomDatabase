package com.capgemini.starterkit.roomdatabase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capgemini.starterkit.roomdatabase.repository.ProjectRepository
import kotlinx.coroutines.launch

class ProjectViewModel( private val project_repository: ProjectRepository
) : ViewModel() {

    fun insertValues() {
        viewModelScope.launch {
            project_repository.insertProjectvalues()
        }
    }
    fun getUserProjectValues(){
        viewModelScope.launch{
            project_repository.getUserWithProjectValues()
        }
    }
}