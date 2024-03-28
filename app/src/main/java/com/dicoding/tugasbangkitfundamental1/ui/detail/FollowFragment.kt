package com.dicoding.tugasbangkitfundamental1.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tugasbangkitfundamental1.data.remote.response.Users
import com.dicoding.tugasbangkitfundamental1.databinding.FragmentFollowBinding
import com.dicoding.tugasbangkitfundamental1.ui.UserAdapter


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private val followViewModel by viewModels<FollowViewModel>()

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "position"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_POSITION, 0)
        val username = requireActivity().intent.getStringExtra(DetailActivity.EXTRA_LOGIN)

        val layoutManager = LinearLayoutManager(context)
        binding.follow.layoutManager = layoutManager

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

//        followViewModel.isEmpty.observe(viewLifecycleOwner) {
//            binding.tvNoData.visibility = if (it)View.GONE else View.VISIBLE
//        }

        followViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }


        if (index == 1) {
            followViewModel.getFollowers(username.toString())
            followViewModel.listFollowers.observe(viewLifecycleOwner) {
                setData(it)
            }
        } else {
            followViewModel.getFollowing(username.toString())
            followViewModel.listFollowing.observe(viewLifecycleOwner) {
                setData(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setData(list: List<Users>) {
        val adapter = UserAdapter()
        adapter.submitList(list)
        binding.follow.adapter = adapter
    }

}