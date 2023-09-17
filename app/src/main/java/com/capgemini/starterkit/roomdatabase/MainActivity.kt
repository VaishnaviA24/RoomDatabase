package com.capgemini.starterkit.roomdatabase

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.capgemini.starterkit.roomdatabase.adapter.MainInfoAdapter
import com.capgemini.starterkit.roomdatabase.databinding.ActivityMainBinding
import com.capgemini.starterkit.roomdatabase.room.MainInfoEntity
import com.capgemini.starterkit.roomdatabase.viewmodel.MainViewModel
import com.capgemini.starterkit.roomdatabase.viewmodel.MainViewModelFactory
import java.util.Collections
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {

    private val binding: ActivityMainBinding by lazy{
        DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
    }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((MainApplication.getApplicationInstance()!!.repository))
    }

    private val mainInfoAdapter = MainInfoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
    }

    private fun initObservers() {
        viewModel.getAllData.observe(this, Observer { userInfo ->
            Collections.reverse(userInfo)
            mainInfoAdapter.submitList(userInfo)
        })
    }

    private fun setAdapterListener() {
        binding.recyclerview.adapter = mainInfoAdapter

        //deleting the row
        mainInfoAdapter.itemClickListener = {
            viewModel.delete(it.id)
        }
    }

    private fun buttonClickListener() {
        binding.buttonSave.setOnClickListener {
            viewModel.insertData(MainInfoEntity(name = binding.userName.text.toString(), email =  binding.email.text.toString()))
            binding.userName.text.clear()
            binding.email.text.clear()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun validateField() {
        if (isValidEmail(binding.email.text.toString()) && binding.userName.text.isNotEmpty()) {
            binding.buttonSave.isEnabled = true
            binding.buttonSave.setBackgroundColor(getColor(R.color.save_button))
        } else {
            binding.buttonSave.isEnabled = false
            binding.buttonSave.setBackgroundColor(getColor(R.color.colorPrimary))
        }
    }
}
