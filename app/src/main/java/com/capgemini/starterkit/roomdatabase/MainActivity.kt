package com.capgemini.starterkit.roomdatabase

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.capgemini.starterkit.roomdatabase.adapter.EmployeeAdapter
import com.capgemini.starterkit.roomdatabase.databinding.ActivityMainBinding
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity
import com.capgemini.starterkit.roomdatabase.viewmodel.EmployeeViewModel
import com.capgemini.starterkit.roomdatabase.viewmodel.EmpViewModelFactory
import com.capgemini.starterkit.roomdatabase.viewmodel.ProjectViewModel
import com.capgemini.starterkit.roomdatabase.viewmodel.ProjectViewModelFactory
import java.util.Collections
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
    }
    private val viewModel: EmployeeViewModel by viewModels {
        EmpViewModelFactory((MainApplication.getApplicationInstance()!!.repository))
    }
    private val projectViewModel: ProjectViewModel by viewModels {
        ProjectViewModelFactory((MainApplication.getApplicationInstance()!!.proj_repository))
    }
    private val employeeAdapter = EmployeeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        projectViewModel.insertProjects()
        buttonClickListener()
        editTextClickListener()
        setAdapterListener()
        initObservers()
    }

    private fun editTextClickListener() {
        //To check if the email is valid or not
        binding.email.addTextChangedListener {
            validateField()
        }

        binding.userName.addTextChangedListener {
            validateField()
        }

        binding.projectId.addTextChangedListener {
            validateField()
        }
    }

    private fun initObservers() {
        viewModel.getAllData.observe(this) { userInfo ->
            Collections.reverse(userInfo)
            employeeAdapter.submitList(userInfo)
        }
    }

    private fun setAdapterListener() {
        binding.recyclerview.adapter = employeeAdapter

        //deleting the row
        employeeAdapter.itemClickListener = {
            viewModel.delete(it.empId)
        }
    }

    private fun buttonClickListener() {
        binding.buttonSave.setOnClickListener {
            viewModel.insertData(
                EmployeeEntity(
                    name = binding.userName.text.toString(),
                    email = binding.email.text.toString(),
                    empProjectId = binding.projectId.text.toString()
                )
            )
            binding.userName.text.clear()
            binding.email.text.clear()
            binding.projectId.text.clear()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun validateField() {
        if (isValidEmail(binding.email.text.toString()) &&
            binding.userName.text.isNotEmpty() &&
            binding.projectId.text.isNotEmpty()
        ) {
            binding.buttonSave.isEnabled = true
            binding.buttonSave.setBackgroundColor(getColor(R.color.save_button))
        } else {
            binding.buttonSave.isEnabled = false
            binding.buttonSave.setBackgroundColor(getColor(R.color.colorPrimary))
        }
    }
}
