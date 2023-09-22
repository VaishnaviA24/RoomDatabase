package com.capgemini.starterkit.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.starterkit.roomdatabase.databinding.RecyclerviewItemBinding
import com.capgemini.starterkit.roomdatabase.room.entity.EmployeeEntity

class EmployeeAdapter : ListAdapter<EmployeeEntity,
        EmployeeAdapter.MainInfoViewHolder>(MainInfoComparator()) {

    var itemClickListener: (EmployeeEntity) -> Unit = {}
    lateinit var binding: RecyclerviewItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return MainInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainInfoViewHolder(private val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.deleteIcon.setOnClickListener {
                itemClickListener(getItem(layoutPosition))
            }
        }

        fun bind(employeeEntity: EmployeeEntity) {
            binding.itemProjectId.text = employeeEntity.empProjectId
            binding.itemName.text = employeeEntity.name
            binding.itemEmail.text = employeeEntity.email
        }
    }
}

class MainInfoComparator : DiffUtil.ItemCallback<EmployeeEntity>() {
    override fun areItemsTheSame(oldItem: EmployeeEntity, newItem: EmployeeEntity): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: EmployeeEntity, newItem: EmployeeEntity): Boolean {
        return oldItem.empId == newItem.empId
    }
}