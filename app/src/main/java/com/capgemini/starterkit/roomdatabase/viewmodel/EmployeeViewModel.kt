package com.capgemini.starterkit.roomdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.starterkit.roomdatabase.repository.EmployeeRepository
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeWithProject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeViewModel(
    private val repository: EmployeeRepository
) : ViewModel() {
    val getAllData: LiveData<List<EmployeeEntity>> = repository.allUsersData.asLiveData()
    val searchResult: MutableLiveData<List<EmployeeEntity>> = MutableLiveData()

    fun insertData(employeeEntityInfo: EmployeeEntity) =
        viewModelScope.launch {
        repository.insertData(employeeEntityInfo)
    }

    fun delete(userid: Int) {
        viewModelScope.launch {
            repository.deleteById(userid)
        }
    }

    fun searchEmployeeByIdOrName(searchValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userIds = try {
                listOf(searchValue.toInt())
            } catch (e: NumberFormatException) {
                emptyList()
            }

            val employees = if (userIds.isNotEmpty()) {
                repository.getEmpByIds(userIds)
            } else {
                repository.getEmpByName(searchValue)
            }

            withContext(Dispatchers.Main) {
                searchResult.postValue(employees)
            }
        }
    }

    fun getEmployeeWithProject(projectId: String): LiveData<List<EmployeeWithProject>> {
        return repository.getEmployeeWithProject(projectId).asLiveData()
    }

}