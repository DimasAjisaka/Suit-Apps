package com.aji.suitapps

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.aji.suitapps.databinding.ActivitySecondScreenBinding

class SecondScreenActivity : AppCompatActivity() {
    private var _binding: ActivitySecondScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        view()
        screen = this

        val name = intent.getStringExtra("name")

        binding.backarrow.setOnClickListener {
            finish()
        }
        binding.tvName.text = name ?: "Jhon Doe"
        binding.tvNote.text = intent.getStringExtra(USERNAME) ?: "Selected User Name"
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, ThirdScreenActivity::class.java).putExtra("name", name))
        }
    }

    private fun view() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) window.insetsController?.hide(
            WindowInsets.Type.statusBars()) else window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val NAME = "name"
        const val USERNAME = "username"
        var screen: SecondScreenActivity? = null
    }
}