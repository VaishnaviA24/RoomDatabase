package com.capgemini.starterkit.roomdatabase.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity

@Dao
interface ProjectDao {

    //bulk data insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(projectEntity: List<ProjectEntity>)

    @Update
    suspend fun updateProject(projectEntity: ProjectEntity)

    @Delete
    suspend fun deleteProject(projectEntity: ProjectEntity)

    @Query("SELECT * FROM Project ORDER BY projectName ASC")
    fun getProjectsSortedByName(): List<ProjectEntity>

    //Returns the count of records
    @Query("SELECT COUNT(*) FROM Project")
    fun getProjectCount(): Int

}