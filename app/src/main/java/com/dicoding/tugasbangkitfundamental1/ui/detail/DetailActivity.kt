package com.dicoding.tugasbangkitfundamental1.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.dicoding.tugasbangkitfundamental1.ui.fav.FavActivity
import com.dicoding.tugasbangkitfundamental1.R
import com.dicoding.tugasbangkitfundamental1.data.local.FavUserEntity
import com.dicoding.tugasbangkitfundamental1.databinding.ActivityDetailBinding
import com.dicoding.tugasbangkitfundamental1.ui.adapter.SectionPagerAdapter
import com.dicoding.tugasbangkitfundamental1.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.dicoding.tugasbangkitfundamental1.ui.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private var user: FavUserEntity = FavUserEntity(0, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_LOGIN)

        detailViewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        detailViewModel.getDetailUser(username.toString())
        detailViewModel.favUserEntity.observe(this) {
            user = it
        }

        detailViewModel.detailUsers.observe(this) {
            with(binding) {
                progressBar.isGone = true
                if (it != null) {
                    tvUsername.text = it.login
                    nama.text = it.name
                    jumlahFollowers.text = it.followers.toString()
                    jumlahFollowing.text = it.following.toString()
                    Glide.with(binding.root)
                        .load(it.avatarUrl)
                        .into(binding.profile)
                        .clearOnDetach()
                }
            }
        }

        detailViewModel.isFavorite(username.toString()).observe(this) {
            if (it) {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_favorite_24
                    )
                )
                binding.fabFavorite.setOnClickListener {
                    detailViewModel.deleteFavorite(username.toString())
                }
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_favorite_border_24
                    )
                )
                binding.fabFavorite.setOnClickListener {
                    detailViewModel.addFavorite(user)
                }
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        with(binding) {
            topAppBar.inflateMenu(R.menu.option_menu)
            topAppBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favUser -> {
                        val intent = Intent(this@DetailActivity, FavActivity::class.java)
                        startActivity(intent)
                    }

                    R.id.setting -> {
                        Toast.makeText(this@DetailActivity, "Setting", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }
    }


    companion object {
        const val EXTRA_LOGIN = "login"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}