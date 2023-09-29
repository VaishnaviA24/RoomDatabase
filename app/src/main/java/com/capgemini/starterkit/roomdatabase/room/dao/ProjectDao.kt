package com.capgemini.starterkit.roomdatabase.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    // Bulk data insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjectValue(projectEntity: ProjectEntity)

    @Query("SELECT * FROM Project")
    fun getAllProjects(): Flow<List<ProjectEntity>>

    @Update
    suspend fun updateProject(projectEntity: ProjectEntity)

    @Delete
    suspend fun deleteProject(projectEntity: ProjectEntity)

    @Query("SELECT * FROM Project ORDER BY projectName ASC")
    fun getProjectsSortedByName(): Flow<List<ProjectEntity>>

    @Query("SELECT COUNT(*) FROM Project")
    fun getProjectCount(): LiveData<Int>

}