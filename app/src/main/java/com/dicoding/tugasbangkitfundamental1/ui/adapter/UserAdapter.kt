package com.dicoding.tugasbangkitfundamental1.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.tugasbangkitfundamental1.data.remote.response.Users
import com.dicoding.tugasbangkitfundamental1.databinding.ItemUsersBinding
import com.dicoding.tugasbangkitfundamental1.ui.detail.DetailActivity


class UserAdapter(private val onClick: (Users) -> Unit) : ListAdapter<Users, UserAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        Log.d("UserAdapter", "onBindViewHolder: $user")
        holder.bind(user)
    }

    class ViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.apply {
                binding.nama.text = users.login
                Glide.with(binding.root)
                    .load(users.avatarUrl)
                    .into(binding.profilePicture)
            }
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                Log.d("UserAdapter", "onBindViewHolder: $intent")
                intent.putExtra(DetailActivity.EXTRA_LOGIN, users.login)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Users>() {
            override fun areItemsTheSame(
                oldItem: Users,
                newItem: Users
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Users,
                newItem: Users
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}