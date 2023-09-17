package com.capgemini.starterkit.roomdatabase.repository

import com.capgemini.starterkit.roomdatabase.room.MainEntryDao
import com.capgemini.starterkit.roomdatabase.room.MainInfoEntity
import kotlinx.coroutines.flow.Flow

class MainInfoRepository(private val mainEntryDao: MainEntryDao) {

    var allUsersData: Flow<List<MainInfoEntity>> = mainEntryDao.getAllData()

    suspend fun insertData(userInfo: MainInfoEntity) {
        mainEntryDao.insertData(userInfo)
    }

    suspend fun deleteById(u_id : Int){
        mainEntryDao.deleteById(u_id)
    }

}