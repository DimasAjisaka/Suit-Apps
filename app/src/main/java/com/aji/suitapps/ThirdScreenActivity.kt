package com.aji.suitapps

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aji.suitapps.databinding.ActivityThirdScreenBinding
import com.aji.suitapps.utils.adapters.OnClick
import com.aji.suitapps.utils.adapters.UserAdapter
import com.aji.suitapps.utils.repositories.Response
import com.aji.suitapps.utils.viewmodels.UserViewModel

class ThirdScreenActivity : AppCompatActivity() {
    private var _binding: ActivityThirdScreenBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()
    private var userAdapter: UserAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loading(false)
        view()

        val name = intent.getStringExtra("name")
        userViewModel.list(10)
        data()
        setData()

        binding.backarrow.setOnClickListener {
            finish()
        }

        binding.refresh.setOnRefreshListener {
            userViewModel.list(10)
            binding.refresh.isRefreshing = false
        }

        userAdapter?.setList(object : OnClick{
            override fun onUserClick(username: String) {
                SecondScreenActivity.screen?.finish()
                startActivity(Intent(this@ThirdScreenActivity, SecondScreenActivity::class.java)
                    .putExtra(SecondScreenActivity.USERNAME, username)
                    .putExtra(SecondScreenActivity.NAME, name))
                finish()
            }
        })

    }

    private fun view() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) window.insetsController?.hide(
            WindowInsets.Type.statusBars()) else window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
    }

    private fun data() {
        userViewModel.response.observe(this) {
            if (it != null) {
                when (it) {
                    is Response.Loading -> loading(true)
                    is Response.Success -> {
                        loading(false)
                        it.data?.data?.let { data ->
                            userAdapter?.list(data.toList())
                        }
                        if (it.data?.data?.size == 0) binding.tvInfo.visibility = View.VISIBLE else binding.tvInfo.visibility = View.GONE
                    }
                    is Response.Error -> {
                        loading(false)
                        Toast.makeText(this, "Get List Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setData() {
        userAdapter = UserAdapter()
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvUser.adapter = userAdapter
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.addOnScrollListener(listener)
    }

    private val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) userViewModel.list(10)
        }
    }

    private fun loading(isLoad: Boolean) {
        if (isLoad) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}