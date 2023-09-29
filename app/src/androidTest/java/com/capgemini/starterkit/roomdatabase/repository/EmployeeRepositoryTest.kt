package com.capgemini.starterkit.roomdatabase.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capgemini.starterkit.roomdatabase.room.dao.EmployeeDao
import com.capgemini.starterkit.roomdatabase.room.dao.ProjectDao
import com.capgemini.starterkit.roomdatabase.room.database.MyDatabase
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
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
    private lateinit var projectDao: ProjectDao
    private lateinit var employeeRepository: EmployeeRepository
    private lateinit var projectRepository: ProjectRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MyDatabase::class.java).build()
        employeeDao = database.dataEntryDao()
        projectDao = database.projectDao()
        employeeRepository = EmployeeRepository(employeeDao)
        projectRepository = ProjectRepository(projectDao)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertDataAndGetAllData() = runBlocking {
        val employee1 =
            EmployeeEntity(
                empId = 1,
                name = "Vaishnavi",
                email = "vaishnavi@example.com",
                empProjectId = "1001"
            )
        val employee2 =
            EmployeeEntity(
                empId = 2,
                name = "Radhika",
                email = "radhika@example.com",
                empProjectId = "2002"
            )
        val employee3 =
            EmployeeEntity(
                empId = 3,
                name = "Veeresh",
                email = "veeresh@example.com",
                empProjectId = "3003"
            )
        employeeRepository.insertData(employee1)
        employeeRepository.insertData(employee2)
        employeeRepository.insertData(employee3)


        val allData = employeeRepository.allUsersData.first()
        Log.d("EmployeeRepositoryTest", "insertDataAndGetAllData - List size: ${allData.size}")
        assertEquals(3, allData.size)
    }

    @Test
    fun deleteById() = runBlocking {
        // Arrange: Insert an EmployeeEntity
        val employee1 =
            EmployeeEntity(
                empId = 1,
                name = "Vaishnavi",
                email = "vaishnavi@example.com",
                empProjectId = "1001"
            )
        val employee2 =
            EmployeeEntity(
                empId = 2,
                name = "Radhika",
                email = "radhika@example.com",
                empProjectId = "2002"
            )
        val employee3 =
            EmployeeEntity(
                empId = 3,
                name = "Veeresh",
                email = "veeresh@example.com",
                empProjectId = "3003"
            )
        employeeRepository.insertData(employee1)
        employeeRepository.insertData(employee2)
        employeeRepository.insertData(employee3)

        delay(1000) // Wait for insert to complete

        employeeRepository.deleteById(1)

        val allData = employeeRepository.allUsersData.first()
        Log.d("EmployeeRepositoryTest", "deleteById - List size: ${allData.size}")
        assertEquals(2, allData.size)
    }

    @Test
    fun getEmpByIds() = runBlocking {

        val employee1 =
            EmployeeEntity(
                empId = 1,
                name = "Vaishnavi",
                email = "vaishnavi@example.com",
                empProjectId = "1001"
            )
        val employee2 =
            EmployeeEntity(
                empId = 2,
                name = "Radhika",
                email = "radhika@example.com",
                empProjectId = "2002"
            )
        val employee3 =
            EmployeeEntity(
                empId = 3,
                name = "Veeresh",
                email = "veeresh@example.com",
                empProjectId = "3003"
            )

        employeeRepository.insertData(employee1)
        employeeRepository.insertData(employee2)
        employeeRepository.insertData(employee3)

        val empIdsToRetrieve = listOf(1, 3)
        val retrievedEmployees = employeeRepository.getEmpByIds(empIdsToRetrieve)

        Log.d("EmployeeRepositoryTest", "getEmpByIds - List size: ${retrievedEmployees.size}")
        assertEquals(2, retrievedEmployees.size)
        assertTrue(retrievedEmployees.contains(employee1))
        assertTrue(retrievedEmployees.contains(employee3))
    }

    @Test
    fun getEmployeeWithProject() = runBlocking {

        // Arrange: Insert a project and employees
        val project1 = ProjectEntity(projectId = "1001", projectName = "Sample Project1")
        val project2 = ProjectEntity(projectId = "1002", projectName = "Sample Project2")
        projectRepository.insertProjectValue(project1)
        projectRepository.insertProjectValue(project2)

        val employee1 = EmployeeEntity(
            empId = 1,
            name = "Vaishnavi",
            email = "vaishnavi@example.com",
            empProjectId = "1001"
        )
        val employee2 = EmployeeEntity(
            empId = 2,
            name = "Rekha",
            email = "rekha@example.com",
            empProjectId = "1001"
        )
        val employee3 = EmployeeEntity(
            empId = 2,
            name = "Rekha12",
            email = "rekha12@example.com",
            empProjectId = "1001"
        )
        employeeRepository.insertData(employee1)
        employeeRepository.insertData(employee2)
        employeeRepository.insertData(employee3)

        val getData = employeeRepository.getEmployeeWithProject("1001").first()
        Log.d("EmployeeRepositoryTest", "getEmployeeWithProject - List size: ${getData.size}")
        assertEquals(2, getData.size)
    }
}