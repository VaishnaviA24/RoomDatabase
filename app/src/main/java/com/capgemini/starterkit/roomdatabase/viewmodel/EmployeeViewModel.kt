package com.capgemini.starterkit.roomdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.starterkit.roomdatabase.repository.EmployeeRepository
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeWithProject
import kotlinx.coroutines.launch

class EmployeeViewModel(
    private val repository: EmployeeRepository
) : ViewModel() {

    // Creates a LiveData that has values collected from the origin Flow.
    val getAllData: LiveData<List<EmployeeEntity>> = repository.allUsersData.asLiveData()

    //Launching a new coroutine to insert the data in a non-blocking way
    fun insertData(employeeEntityInfo: EmployeeEntity) = viewModelScope.launch {
        repository.insertData(employeeEntityInfo)
    }

    fun delete(userid: Int) {
        viewModelScope.launch {
            repository.deleteById(userid)
        }
    }

    fun getEmpByIds(userIds: List<Int>) {
        viewModelScope.launch {
            repository.getEmpByIds(userIds)
        }
    }

    fun getEmpByName(searchName: String) {
        viewModelScope.launch {
            repository.getEmpByName(searchName)
        }
    }

    fun getEmployeeWithProject(projectId: String): List<EmployeeWithProject> {
        return repository.getEmployeeWithProject(projectId)
    }
}


//    fun getEmployeeWithProject(employeeId: Int): LiveData<EmployeeWithProject> {
//        val liveData = MutableLiveData<EmployeeWithProject>()
//
//        viewModelScope.launch {
//            val result = repository.getEmployeeWithProject(employeeId)
//            liveData.postValue(result)
//        }
//
//        return liveData
//    }
//
//    fun getEmployeeWithMultipleProjects(employeeId: Int): LiveData<EmployeeWithMultipleProjects> {
//        val liveData = MutableLiveData<EmployeeWithMultipleProjects>()
//
//        viewModelScope.launch {
//            val result = repository.getEmployeeWithMultipleProjects(employeeId)
//            liveData.postValue(result)
//        }
//
//        return liveData
//    }