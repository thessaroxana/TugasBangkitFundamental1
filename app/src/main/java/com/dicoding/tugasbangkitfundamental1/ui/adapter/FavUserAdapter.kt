package com.dicoding.tugasbangkitfundamental1.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.tugasbangkitfundamental1.data.local.FavUserEntity
import com.dicoding.tugasbangkitfundamental1.databinding.FavItemUserBinding

class FavUserAdapter (
    private val onClick: (FavUserEntity) -> Unit,
    private val onDelete: (FavUserEntity) -> Unit
) : ListAdapter<FavUserEntity, FavUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FavItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onClick(user)
        }
        holder.binding.btnDelete.setOnClickListener {
            onDelete(user)
        }
    }

    class MyViewHolder(val binding: FavItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavUserEntity) {
            binding.nama.text = user.username
            binding.btnDelete.isVisible = true
            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.profilePicture)
                .clearOnDetach()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavUserEntity>() {
            override fun areItemsTheSame(oldItem: FavUserEntity, newItem: FavUserEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FavUserEntity,
                newItem: FavUserEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}