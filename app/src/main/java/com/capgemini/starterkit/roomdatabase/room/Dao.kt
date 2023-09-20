package com.capgemini.starterkit.roomdatabase.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(user: User)

    @Query("SELECT * FROM user_info")
    fun getAllData(): Flow<List<User>>

    @Query("delete from user_info where user_id = :u_id")
    suspend fun deleteById(u_id : Int)

    @Query("SELECT * FROM user_info WHERE user_id IN (:userIds)")
    fun getUsersByIds(userIds: List<Long>): List<User>

}

@Dao
interface ProjectDao {
    @Insert
    suspend fun insertProject(project: List<Project>)

    @Update
    suspend fun update(project: Project)

    @Delete
    suspend fun delete(project: Project)

    @Query("SELECT * FROM project_details WHERE projectId = :id")
    suspend fun getProjectById(id: Long): Project?

    @Query("SELECT * FROM project_details WHERE projectName LIKE :searchName")
    fun getProjectByName(searchName: String): List<Project>

    @Query("SELECT * FROM project_details ORDER BY projectName ASC")
    fun getProjectsSortedByName(): List<Project>

    //Returns the count of records
    @Query("SELECT COUNT(*) FROM project_details")
    fun getProjectCount(): Int

    //One-to-One Relationship
    @Transaction
    @Query("SELECT * FROM user_info")
    fun getUserWithProject(): LiveData<UserWithProject>

    //One-to-Many Relationship
    @Transaction
    @Query("SELECT * FROM project_details")
    fun getUserWithMultipleProjects(): List<UserWithMultipleProjects>

}