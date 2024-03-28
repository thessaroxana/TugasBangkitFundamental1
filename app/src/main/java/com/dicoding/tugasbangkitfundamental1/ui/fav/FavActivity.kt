package com.dicoding.tugasbangkitfundamental1.ui.fav

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tugasbangkitfundamental1.databinding.ActivityFavBinding
import com.dicoding.tugasbangkitfundamental1.ui.detail.DetailActivity
import com.dicoding.tugasbangkitfundamental1.ui.adapter.FavUserAdapter
import com.dicoding.tugasbangkitfundamental1.ui.viewmodel.FavoriteViewModel
import com.dicoding.tugasbangkitfundamental1.ui.viewmodel.ViewModelFactory

class FavActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavBinding
    private lateinit var adapter: FavUserAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserGithub.layoutManager = layoutManager

        adapter = FavUserAdapter({
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, it.username)
            startActivity(intent)
        }, {
            favoriteViewModel.deleteAllFavorite(it.username.toString())
            favoriteViewModel.getAllFavorite().observe(this) { result ->
                adapter.submitList(result)
            }
        })

        binding.rvUserGithub.adapter = adapter
        favoriteViewModel.getAllFavorite().observe(this){result ->
            binding.progressBar.isGone = true
            adapter.submitList(result)
            if(result.isEmpty()){
                binding.tvEmpty.isVisible = true
            }
        }
    }
}