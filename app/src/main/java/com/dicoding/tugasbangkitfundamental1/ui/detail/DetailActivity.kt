package com.dicoding.tugasbangkitfundamental1.ui.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.tugasbangkitfundamental1.R
import com.dicoding.tugasbangkitfundamental1.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_LOGIN = "LOGIN"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_LOGIN)

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        supportActionBar?.elevation = 0f

        detailViewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        detailViewModel.getDetailUsers(username!!.toString())

        detailViewModel.detailUsers.observe(this) {
            with(binding) {
                tvUsername.text = it.login
                tvNama.text = it.name
                jumlahFollowers.text = it.followers.toString()
                jumlahFollowing.text = it.following.toString()
                Glide.with(binding.root)
                    .load(it.avatarUrl)
                    .into(binding.ivProfilePicture)
                    .clearOnDetach()
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }
}