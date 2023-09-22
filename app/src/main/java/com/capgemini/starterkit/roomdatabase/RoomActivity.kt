package com.capgemini.starterkit.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.starterkit.roomdatabase.adapter.EmployeeListAdapter
import com.capgemini.starterkit.roomdatabase.databinding.ActivityRoomBinding
import com.capgemini.starterkit.roomdatabase.repository.EmployeeRepository
import com.capgemini.starterkit.roomdatabase.room.dao.EmployeeDao
import com.capgemini.starterkit.roomdatabase.room.database.MyDatabase
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.viewmodel.EmployeeViewModel

class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var employeeDao: EmployeeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MyDatabase.getDatabase(this)
        employeeDao = database.dataEntryDao()
        employeeViewModel = EmployeeViewModel(EmployeeRepository(employeeDao))


        binding.btnAddemp.setOnClickListener{
            binding.container.removeAllViews()
            val employeeForm = LayoutInflater.from(this).inflate(R.layout.employee_form, binding.container, true)

            val btnSave = employeeForm.findViewById<Button>(R.id.btn_empSave)
            val userName = employeeForm.findViewById<EditText>(R.id.userName)
            val email = employeeForm.findViewById<EditText>(R.id.email)
            val projectId = employeeForm.findViewById<EditText>(R.id.projectId)

            btnSave.setOnClickListener {
                val name = userName.text.toString()
                val userEmail = email.text.toString()
                val projectID = projectId.text.toString()
                val employee = EmployeeEntity(name = name, email = userEmail, empProjectId = projectID)

                // Insert the employee data into the database
                employeeViewModel.insertData(employee)

                // Clear the form
                userName.text.clear()
                email.text.clear()
                projectId.text.clear()
            }
        }

        binding.btnDisplayemp.setOnClickListener {
            binding.container.removeAllViews()

            // Inflate the employee list layout
            val employeeListLayout = LayoutInflater.from(this).inflate(R.layout.employee_db, binding.container, false)

            // Add the RecyclerView to the container
            binding.container.addView(employeeListLayout)

            // Get the RecyclerView from the layout
            val recyclerView = employeeListLayout.findViewById<RecyclerView>(R.id.rv_empData)

            // Set a LinearLayoutManager for the RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this)

            // Create an adapter and set it to the RecyclerView
            val adapter = EmployeeListAdapter()
            recyclerView.adapter = adapter

            // Observe the LiveData in the ViewModel and update the adapter when data changes
            employeeViewModel.getAllData.observe(this) { employees ->
                adapter.submitList(employees)
            }
        }

        binding.btnAddproject.setOnClickListener{
            binding.container.removeAllViews()
            LayoutInflater.from(this).inflate(R.layout.project_form, binding.container, true)
        }


        binding.btnDisplayproj.setOnClickListener{
            binding.container.removeAllViews()
            LayoutInflater.from(this).inflate(R.layout.project_db, binding.container, true)
        }

    }
}