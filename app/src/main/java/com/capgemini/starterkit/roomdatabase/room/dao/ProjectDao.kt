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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(projectEntity: List<ProjectEntity>)

    @Update
    suspend fun update(projectEntity: ProjectEntity)

    @Delete
    suspend fun delete(projectEntity: ProjectEntity)

    @Query("SELECT * FROM Project ORDER BY projectName ASC")
    fun getProjectsSortedByName(): List<ProjectEntity>

    //Returns the count of records
    @Query("SELECT COUNT(*) FROM Project")
    fun getProjectCount(): Int

}