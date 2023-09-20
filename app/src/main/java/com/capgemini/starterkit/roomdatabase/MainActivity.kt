package com.capgemini.starterkit.roomdatabase

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.capgemini.starterkit.roomdatabase.adapter.UserAdapter
import com.capgemini.starterkit.roomdatabase.databinding.ActivityMainBinding
import com.capgemini.starterkit.roomdatabase.room.User
import com.capgemini.starterkit.roomdatabase.viewmodel.UserViewModel
import com.capgemini.starterkit.roomdatabase.viewmodel.UserViewModelFactory
import com.capgemini.starterkit.roomdatabase.viewmodel.ProjectViewModel
import com.capgemini.starterkit.roomdatabase.viewmodel.ProjectViewModelFactory
import java.util.Collections
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
    }
    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory((MainApplication.getApplicationInstance()!!.repository))
    }
    private val projectViewModel: ProjectViewModel by viewModels {
        ProjectViewModelFactory((MainApplication.getApplicationInstance()!!.proj_repository))
    }
    private val userAdapter = UserAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        projectViewModel.insertValues()
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
            userAdapter.submitList(userInfo)
        }
    }

    private fun setAdapterListener() {
        binding.recyclerview.adapter = userAdapter

        //deleting the row
        userAdapter.itemClickListener = {
            viewModel.delete(it.id)
        }
    }

    private fun buttonClickListener() {
        binding.buttonSave.setOnClickListener {
            viewModel.insertData(
                User(
                    name = binding.userName.text.toString(),
                    email = binding.email.text.toString(),
                    userProjectId = binding.projectId.text.toString()
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
