package com.capgemini.starterkit.roomdatabase.repository

import com.capgemini.starterkit.roomdatabase.room.dao.EmployeeDao
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeWithProject
import kotlinx.coroutines.flow.Flow

class EmployeeRepository(private val employeeDao: EmployeeDao) {

    var allUsersData: Flow<List<EmployeeEntity>> = employeeDao.getAllData()

    suspend fun insertData(employeeEntityInfo: EmployeeEntity) {
        employeeDao.insertData(employeeEntityInfo)
    }

    suspend fun deleteById(uId: Int) {
        employeeDao.deleteById(uId)
    }

    fun getEmpByIds(userIds: List<Int>): List<EmployeeEntity> {
        return employeeDao.getEmpByIds(userIds)
    }

    fun getEmpByName(searchName: String): List<EmployeeEntity> {
        return employeeDao.getEmpByName(searchName)
    }

    fun getEmployeeWithProject(projectId: String): List<EmployeeWithProject> {
        return employeeDao.getEmployeeWithProject(projectId)
    }
}