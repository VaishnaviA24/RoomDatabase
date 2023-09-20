package com.capgemini.starterkit.roomdatabase.repository

import com.capgemini.starterkit.roomdatabase.room.UserDao
import com.capgemini.starterkit.roomdatabase.room.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    var allUsersData: Flow<List<User>> = userDao.getAllData()

    suspend fun insertData(userInfo: User) {
        userDao.insertData(userInfo)
    }

    suspend fun deleteById(u_id : Int){
        userDao.deleteById(u_id)
    }

}