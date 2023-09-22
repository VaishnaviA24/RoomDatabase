package com.capgemini.starterkit.roomdatabase.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capgemini.starterkit.roomdatabase.room.dao.EmployeeDao
import com.capgemini.starterkit.roomdatabase.room.database.MyDatabase
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmployeeRepositoryTest {

    private lateinit var database: MyDatabase
    private lateinit var employeeDao: EmployeeDao
    private lateinit var employeeRepository: EmployeeRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MyDatabase::class.java).build()
        employeeDao = database.dataEntryDao()
        employeeRepository = EmployeeRepository(employeeDao)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertDataAndGetAllData() = runBlocking {
        val employee = EmployeeEntity(
            empId = 1,
            name = "Vaishnavi",
            email = "vaishnavi@example.com",
            empProjectId = "1001"
        )
        employeeRepository.insertData(employee)

        // Assert: Get all data and verify it contains the inserted EmployeeEntity
        val allData = employeeRepository.allUsersData.first()
        Log.d("EmployeeRepositoryTest", "insertDataAndGetAllData - List size: ${allData.size}")
        assertEquals(1, allData.size)
        assertEquals(employee, allData[0])
    }
}