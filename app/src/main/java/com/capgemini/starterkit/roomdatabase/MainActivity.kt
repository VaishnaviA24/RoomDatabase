package com.capgemini.starterkit.roomdatabase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.starterkit.roomdatabase.adapter.EmployeeListAdapter
import com.capgemini.starterkit.roomdatabase.adapter.ProjectListAdapter
import com.capgemini.starterkit.roomdatabase.databinding.ActivityMainBinding
import com.capgemini.starterkit.roomdatabase.repository.EmployeeRepository
import com.capgemini.starterkit.roomdatabase.repository.ProjectRepository
import com.capgemini.starterkit.roomdatabase.room.dao.EmployeeDao
import com.capgemini.starterkit.roomdatabase.room.dao.ProjectDao
import com.capgemini.starterkit.roomdatabase.room.database.MyDatabase
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import com.capgemini.starterkit.roomdatabase.viewmodel.EmployeeViewModel
import com.capgemini.starterkit.roomdatabase.viewmodel.ProjectViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var employeeDao: EmployeeDao
    private lateinit var projectViewModel: ProjectViewModel
    private lateinit var projectDao: ProjectDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MyDatabase.getDatabase(this)
        employeeDao = database.dataEntryDao()
        employeeViewModel = EmployeeViewModel(EmployeeRepository(employeeDao))

        projectDao = database.projectDao()
        projectViewModel = ProjectViewModel(ProjectRepository(projectDao))
        projectViewModel.insertProjects()


        binding.btnAddEmp.setOnClickListener {
            binding.container.removeAllViews()

            binding.etEmpSearchValue.visibility = View.GONE
            binding.btnSearchEmp.visibility = View.GONE
            binding.btnJoinQuery.visibility = View.GONE
            binding.spinnerProjects.visibility = View.GONE
            binding.tvProjCount.visibility = View.GONE
            binding.btnAscProjValue.visibility = View.GONE

            addEmployee()
        }

        binding.btnDisplayEmp.setOnClickListener {
            binding.container.removeAllViews()

            binding.etEmpSearchValue.visibility = View.VISIBLE
            binding.btnSearchEmp.visibility = View.VISIBLE
            binding.btnJoinQuery.visibility = View.VISIBLE
            binding.spinnerProjects.visibility = View.VISIBLE
            binding.tvProjCount.visibility = View.GONE
            binding.btnAscProjValue.visibility = View.GONE

            displayEmployeeData()
        }

        binding.btnAddProj.setOnClickListener {
            binding.container.removeAllViews()

            binding.etEmpSearchValue.visibility = View.GONE
            binding.btnSearchEmp.visibility = View.GONE
            binding.btnJoinQuery.visibility = View.GONE
            binding.spinnerProjects.visibility = View.GONE
            binding.tvProjCount.visibility = View.GONE
            binding.btnAscProjValue.visibility = View.GONE

            addProject()
        }

        binding.btnDisplayProj.setOnClickListener {
            binding.container.removeAllViews()

            binding.etEmpSearchValue.visibility = View.GONE
            binding.btnSearchEmp.visibility = View.GONE
            binding.btnJoinQuery.visibility = View.GONE
            binding.spinnerProjects.visibility = View.GONE
            binding.tvProjCount.visibility = View.VISIBLE
            binding.btnAscProjValue.visibility = View.VISIBLE

            displayProjectData()

            projectViewModel.projectCount.observe(this) { count ->
                count?.let {
                    binding.tvProjCount.text = getString(R.string.count_label, it)
                }
            }
        }

        binding.btnSearchEmp.setOnClickListener {
            val searchValue = binding.etEmpSearchValue.text.toString()
            if (searchValue.isNotBlank()) {
                employeeViewModel.searchEmployeeByIdOrName(searchValue)
            } else {
                showToast("Please enter a valid employee ID or name")
            }
        }
        employeeViewModel.searchResult.observe(this) { employees ->
            if (employees.isNotEmpty()) {
                // Display search results in the RecyclerView
                displaySearchResults(employees)
            } else {
                showToast(getString(R.string.error_not_found))
            }
        }

        //assigning data to spinner - for join query
        projectViewModel.getAllProjData.observe(this) { projects ->
            val projectIds = projects.map { it.projectId }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, projectIds)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerProjects.adapter = adapter
        }

        binding.btnJoinQuery.setOnClickListener {
            val selectedProjectId = binding.spinnerProjects.selectedItem as String
            employeeViewModel.getEmployeeWithProject(selectedProjectId).observe(this) { employeesWithProject ->
                if (employeesWithProject.isNotEmpty()) {
                    // Display the result in the logcat
                    for (employeeWithProject in employeesWithProject) {
                        Log.d(
                            "EmployeeWithProject",
                            "Employee: ${employeeWithProject.employees.joinToString { it.name }} Project: ${employeeWithProject.projectEntity.projectName}"
                        )
                    }
                    // Show a toast with the count of data displayed
                    showToast("Displayed ${employeesWithProject.size} records")
                } else {
                    showToast("No data found for the project ID: $selectedProjectId")
                }
            }
        }
    }

    private fun displayProjectData() {
        val projectListLayout = LayoutInflater.from(this)
            .inflate(R.layout.project_db, binding.container, false)

        binding.container.addView(projectListLayout)

        val recyclerView = projectListLayout.findViewById<RecyclerView>(R.id.rvProjData)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ProjectListAdapter(
            editClickListener = { project ->
                updateProjectDialog(project)
            },
            deleteClickListener = { project ->
                projectViewModel.deleteById(project)
            }
        )
        recyclerView.adapter = adapter

        projectViewModel.getAllProjData.observe(this) { projects ->
            adapter.submitList(projects)
        }

        binding.btnAscProjValue.setOnClickListener {
            // Sort projects by name in ascending order
            projectViewModel.getProjectsSortedByName().onEach { sortedProjects ->
                adapter.submitList(sortedProjects)
            }.launchIn(lifecycleScope) // Observe using lifecycleScope
            showToast("The table is set in Ascending order according to the project name")
        }
    }

    private fun displayEmployeeData() {
        val employeeListLayout =
            LayoutInflater.from(this)
                .inflate(R.layout.employee_db, binding.container, false)

        binding.container.addView(employeeListLayout)

        val recyclerView = employeeListLayout.findViewById<RecyclerView>(R.id.rvEmpData)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EmployeeListAdapter(
            deleteClickListener = { employee ->
                employeeViewModel.delete(employee.empId)
            }
        )
        recyclerView.adapter = adapter

        employeeViewModel.getAllData.observe(this) { employees ->
            adapter.submitList(employees)
        }
    }

    private fun displaySearchResults(employees: List<EmployeeEntity>?) {
        val employeeListLayout = LayoutInflater.from(this)
            .inflate(R.layout.employee_db, binding.container, false)

        binding.container.removeAllViews()
        binding.container.addView(employeeListLayout)

        val recyclerView = employeeListLayout.findViewById<RecyclerView>(R.id.rvEmpData)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EmployeeListAdapter(
            deleteClickListener = { employee ->
                employeeViewModel.delete(employee.empId)
            }
        )
        recyclerView.adapter = adapter

        adapter.submitList(employees)
    }

    private fun addProject() {
        val projectForm = LayoutInflater.from(this)
            .inflate(R.layout.project_form, binding.container, true)

        val btnSaveProject = projectForm.findViewById<Button>(R.id.btn_projSave)
        val projName = projectForm.findViewById<EditText>(R.id.projName)
        val projId = projectForm.findViewById<EditText>(R.id.projectId)

        btnSaveProject.setOnClickListener {
            val projectName = projName.text.toString()
            val projectId = projId.text.toString()

            val projectIdValue = projectId.ifEmpty { "DEFAULT" }

            val projectEntity = ProjectEntity(
                projectName = projectName,
                projectId = projectIdValue,
            )
            projectViewModel.insertProjectValue(projectEntity)

            projName.text.clear()
            projId.text.clear()
        }
    }

    private fun addEmployee() {
        val employeeForm =
            LayoutInflater.from(this)
                .inflate(R.layout.employee_form, binding.container, true)

        val btnSave = employeeForm.findViewById<Button>(R.id.btnEmpSave)
        val userName = employeeForm.findViewById<EditText>(R.id.userName)
        val email = employeeForm.findViewById<EditText>(R.id.email)
        val projectIdSpinner = employeeForm.findViewById<Spinner>(R.id.spinnerProjectId)

        //assigning data to spinner for employee Form
        projectViewModel.getAllProjData.observe(this) { projects ->
            val projectIds = projects.map { it.projectId }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, projectIds)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            projectIdSpinner.adapter = adapter
        }


        btnSave.setOnClickListener {

            val name = userName.text.toString()
            val userEmail = email.text.toString()
            val selectedProjectId = projectIdSpinner.selectedItem as String

            val employee =
                EmployeeEntity(name = name, email = userEmail, empProjectId = selectedProjectId)


            employeeViewModel.insertData(employee)

            userName.text.clear()
            email.text.clear()
        }
    }

    private fun updateProjectDialog(project: ProjectEntity) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.edit_project_dialog, null)

        dialogBuilder.setView(dialogView)

        val etProjectId = dialogView.findViewById<EditText>(R.id.etNewProjId)
        val etProjectName = dialogView.findViewById<EditText>(R.id.etNewProjName)
        val btnUpdate = dialogView.findViewById<Button>(R.id.btnUpdateProject)

        etProjectId.setText(project.projectId)
        etProjectName.setText(project.projectName)

        dialogBuilder.setTitle("Edit Project")

        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = dialogBuilder.create()
        dialog.show()

        btnUpdate.setOnClickListener {
            val updatedProjectId = etProjectId.text.toString()
            val updatedProjectName = etProjectName.text.toString()
            val updatedProject =
                project.copy(projectId = updatedProjectId, projectName = updatedProjectName)
            projectViewModel.updateById(updatedProject)
            dialog.dismiss()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}