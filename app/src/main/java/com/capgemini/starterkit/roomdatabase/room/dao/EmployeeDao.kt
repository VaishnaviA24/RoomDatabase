package com.capgemini.starterkit.roomdatabase.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.capgemini.starterkit.roomdatabase.room.entity.EmpWithMultipleProjects
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeWithProject
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(employeeEntity: EmployeeEntity)

    @Query("SELECT * FROM Employee")
    fun getAllData(): Flow<List<EmployeeEntity>>

    @Query("delete from Employee where empId = :u_id")
    suspend fun deleteById(u_id: Int)

    @Query("SELECT * FROM Employee WHERE empId IN (:userIds)")
    fun getEmpByIds(userIds: List<Long>): List<EmployeeEntity>

    @Query("SELECT * FROM Employee WHERE name LIKE :searchName")
    fun getEmpByName(searchName: String): List<EmployeeEntity>

    //One-to-One Relationship
    @Transaction
    @Query("SELECT * FROM Employee")
    fun getUserWithProject(): List<EmployeeWithProject>

    //One-to-Many Relationship
    @Transaction
    @Query("SELECT * FROM Project")
    fun getUserWithMultipleProjects(): List<EmpWithMultipleProjects>

}