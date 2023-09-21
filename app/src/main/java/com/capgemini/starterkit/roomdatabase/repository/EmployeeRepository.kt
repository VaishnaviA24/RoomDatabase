package com.capgemini.starterkit.roomdatabase.repository

import com.capgemini.starterkit.roomdatabase.room.dao.EmployeeDao
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import kotlinx.coroutines.flow.Flow

class EmployeeRepository(private val employeeDao: EmployeeDao) {

    var allUsersData: Flow<List<EmployeeEntity>> = employeeDao.getAllData()

    suspend fun insertData(employeeEntityInfo: EmployeeEntity) {
        employeeDao.insertData(employeeEntityInfo)
    }

    suspend fun deleteById(u_id: Int) {
        employeeDao.deleteById(u_id)
    }

}