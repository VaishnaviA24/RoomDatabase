package com.capgemini.starterkit.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.starterkit.roomdatabase.databinding.RvprojitemBinding
import com.capgemini.starterkit.roomdatabase.room.entity.ProjectEntity

class ProjectListAdapter(
    private val editClickListener: (ProjectEntity) -> Unit,
    private val deleteClickListener: (ProjectEntity) -> Unit
) : ListAdapter<ProjectEntity,
        ProjectListAdapter.ProjectViewHolder>(ProjectDiffCallback()) {

    private lateinit var binding: RvprojitemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        binding = RvprojitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = getItem(position)
        holder.bind(project)
    }

    inner class ProjectViewHolder(private val binding: RvprojitemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(project: ProjectEntity) {
            binding.tvProjId.text = project.projectId
            binding.tvProjName.text = project.projectName

            binding.icProjEdit.setOnClickListener { editClickListener(project) }
            binding.icProjDelete.setOnClickListener { deleteClickListener(project) }
        }
    }
}

class ProjectDiffCallback : DiffUtil.ItemCallback<ProjectEntity>() {
    override fun areItemsTheSame(oldItem: ProjectEntity, newItem: ProjectEntity): Boolean {
        return oldItem.projectId == newItem.projectId
    }

    override fun areContentsTheSame(oldItem: ProjectEntity, newItem: ProjectEntity): Boolean {
        return oldItem == newItem
    }
}