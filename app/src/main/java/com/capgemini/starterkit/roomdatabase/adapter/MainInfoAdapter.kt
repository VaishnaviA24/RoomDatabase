package com.capgemini.starterkit.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.starterkit.roomdatabase.databinding.RecyclerviewItemBinding
import com.capgemini.starterkit.roomdatabase.room.MainInfoEntity

class MainInfoAdapter : ListAdapter<MainInfoEntity, MainInfoAdapter.MainInfoViewHolder>(MainInfoComparator()) {

    var itemClickListener: (MainInfoEntity) -> Unit = {}
    lateinit var binding: RecyclerviewItemBinding

    inner class MainInfoViewHolder(private val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.deleteIcon.setOnClickListener {
                itemClickListener(getItem(layoutPosition))
            }
        }

        fun bind(mainInfoEntity: MainInfoEntity) {
            binding.itemName.text = mainInfoEntity.name
            binding.itemEmail.text = mainInfoEntity.email
        }
    }


    class MainInfoComparator : DiffUtil.ItemCallback<MainInfoEntity>() {
        override fun areItemsTheSame(oldItem: MainInfoEntity, newItem: MainInfoEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MainInfoEntity, newItem: MainInfoEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return MainInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}