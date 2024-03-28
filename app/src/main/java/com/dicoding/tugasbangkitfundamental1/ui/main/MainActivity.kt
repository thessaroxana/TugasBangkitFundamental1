package com.dicoding.tugasbangkitfundamental1.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tugasbangkitfundamental1.ui.fav.FavActivity
import com.dicoding.tugasbangkitfundamental1.R
import com.dicoding.tugasbangkitfundamental1.ui.setting.SettingActivity
import com.dicoding.tugasbangkitfundamental1.ui.viewmodel.ViewModelFactory
import com.dicoding.tugasbangkitfundamental1.databinding.ActivityMainBinding
import com.dicoding.tugasbangkitfundamental1.ui.detail.DetailActivity
import com.dicoding.tugasbangkitfundamental1.ui.adapter.UserAdapter
import com.dicoding.tugasbangkitfundamental1.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, it.login)
            startActivity(intent)
        }

        binding.userGithub.adapter = adapter

        mainViewModel.isEmpty.observe(this) {
            binding.empty.isVisible = it
        }

        mainViewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }

        mainViewModel.listUser.observe(this) {
            adapter.submitList(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text
                    mainViewModel.findUser(searchView.text.toString())
                    searchView.hide()
                    true
                }
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favUser -> {
                        val action = Intent(this@MainActivity, FavActivity::class.java)
                        startActivity(action)
                    }
                    R.id.setting -> {
                        val action = Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(action)
                    }
                }
                true
            }
        }

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.userGithub.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.userGithub.addItemDecoration(itemDecoration)
    }
}