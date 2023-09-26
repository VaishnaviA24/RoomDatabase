package com.capgemini.starterkit.roomdatabase.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeWithProject
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    //inserting data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(employeeEntity: EmployeeEntity)

    //deleting data
    @Query("delete from Employee where empId = :uId")
    suspend fun deleteById(uId: Int)

    @Query("SELECT * FROM Employee")
    fun getAllData(): Flow<List<EmployeeEntity>>

    //selecting data in bulk
    @Query("SELECT * FROM Employee WHERE empId IN (:userIds)")
    fun getEmpByIds(userIds: List<Int>): List<EmployeeEntity>

    @Query("SELECT * FROM Employee WHERE name LIKE :searchName")
    fun getEmpByName(searchName: String): List<EmployeeEntity>

    @Transaction
    @Query("SELECT * FROM Employee JOIN Project ON Employee.empProjectId = Project.projectId WHERE Employee.empProjectId = :projectId")
    fun getEmployeeWithProject(projectId: String): List<EmployeeWithProject>
}