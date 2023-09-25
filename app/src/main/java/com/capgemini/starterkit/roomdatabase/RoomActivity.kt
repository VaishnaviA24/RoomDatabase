package com.capgemini.starterkit.roomdatabase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.starterkit.roomdatabase.adapter.EmployeeListAdapter
import com.capgemini.starterkit.roomdatabase.adapter.ProjectListAdapter
import com.capgemini.starterkit.roomdatabase.databinding.ActivityRoomBinding
import com.capgemini.starterkit.roomdatabase.repository.EmployeeRepository
import com.capgemini.starterkit.roomdatabase.repository.ProjectRepository
import com.capgemini.starterkit.roomdatabase.room.dao.EmployeeDao
import com.capgemini.starterkit.roomdatabase.room.dao.ProjectDao
import com.capgemini.starterkit.roomdatabase.room.database.MyDatabase
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity
import com.capgemini.starterkit.roomdatabase.viewmodel.EmployeeViewModel
import com.capgemini.starterkit.roomdatabase.viewmodel.ProjectViewModel

class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var employeeDao: EmployeeDao
    private lateinit var projectViewModel: ProjectViewModel
    private lateinit var projectDao: ProjectDao

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MyDatabase.getDatabase(this)
        employeeDao = database.dataEntryDao()
        employeeViewModel = EmployeeViewModel(EmployeeRepository(employeeDao))

        projectDao = database.projectDao()
        projectViewModel = ProjectViewModel(ProjectRepository(projectDao))
        projectViewModel.insertProjects()

        binding.btnAddemp.setOnClickListener {
            binding.container.removeAllViews()
            val employeeForm =
                LayoutInflater.from(this)
                    .inflate(R.layout.employee_form, binding.container, true)

            val btnSave = employeeForm.findViewById<Button>(R.id.btn_empSave)
            val userName = employeeForm.findViewById<EditText>(R.id.userName)
            val email = employeeForm.findViewById<EditText>(R.id.email)
            val projectId = employeeForm.findViewById<EditText>(R.id.projectId)

            btnSave.setOnClickListener {
                val name = userName.text.toString()
                val userEmail = email.text.toString()
                val projectID = projectId.text.toString()

                // If the projectID is empty, setting it to "DEFAULT"
                val projectIdValue = projectID.ifEmpty { "DEFAULT" }

                val employee =
                    EmployeeEntity(name = name, email = userEmail, empProjectId = projectIdValue)

                employeeViewModel.insertData(employee)

                userName.text.clear()
                email.text.clear()
                projectId.text.clear()
            }
        }

        binding.btnDisplayemp.setOnClickListener {
            binding.container.removeAllViews()

            val employeeListLayout =
                LayoutInflater.from(this)
                    .inflate(R.layout.employee_db, binding.container, false)

            binding.container.addView(employeeListLayout)

            val recyclerView = employeeListLayout.findViewById<RecyclerView>(R.id.rv_empData)

            recyclerView.layoutManager = LinearLayoutManager(this)

            val adapter = EmployeeListAdapter()
            recyclerView.adapter = adapter

            employeeViewModel.getAllData.observe(this) { employees ->
                adapter.submitList(employees)
            }
        }

        binding.btnAddproject.setOnClickListener {
            binding.container.removeAllViews()
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

        binding.btnDisplayproj.setOnClickListener {
            binding.container.removeAllViews()
            val projectListLayout = LayoutInflater.from(this)
                .inflate(R.layout.project_db, binding.container, false)

            binding.container.addView(projectListLayout)

            val recyclerView = projectListLayout.findViewById<RecyclerView>(R.id.rv_projData)

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
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun updateProjectDialog(project: ProjectEntity) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.edit_project_dialog, null)

        dialogBuilder.setView(dialogView)

        val etProjectId = dialogView.findViewById<EditText>(R.id.et_newProjId)
        val etProjectName = dialogView.findViewById<EditText>(R.id.et_newProjName)
        val btnUpdate = dialogView.findViewById<Button>(R.id.btn_updateProject)

        etProjectId.setText(project.projectId.toString())
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
}