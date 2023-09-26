package com.capgemini.starterkit.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.starterkit.roomdatabase.databinding.RvempitemBinding
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity

class EmployeeListAdapter : ListAdapter<EmployeeEntity,
        EmployeeListAdapter.EmployeeViewHolder>(EmployeeDiffCallback()) {

    private lateinit var binding: RvempitemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RvempitemBinding.inflate(inflater, parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }

    inner class EmployeeViewHolder(private val binding: RvempitemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(employee: EmployeeEntity) {
            binding.itemName.text = employee.name
            binding.itemEmail.text = employee.email
            binding.itemProjectId.text = employee.empProjectId
        }
    }
}

class EmployeeDiffCallback : DiffUtil.ItemCallback<EmployeeEntity>() {
    override fun areItemsTheSame(oldItem: EmployeeEntity, newItem: EmployeeEntity): Boolean {
        return oldItem.empId == newItem.empId
    }

    override fun areContentsTheSame(oldItem: EmployeeEntity, newItem: EmployeeEntity): Boolean {
        return oldItem == newItem
    }
}