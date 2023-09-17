package com.capgemini.starterkit.roomdatabase.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MainEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(mainInfoEntity: MainInfoEntity)

    @Query("SELECT * FROM user_info")
    fun getAllData(): Flow<List<MainInfoEntity>>

    @Query("delete from user_info where user_id = :u_id")
    suspend fun deleteById(u_id : Int)

}