package com.aji.suitapps

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.aji.suitapps.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        view()

        binding.btnCheck.setOnClickListener {
            val text = binding.tietPalindrome.text.toString()
            when {
                text.isEmpty() -> binding.tietPalindrome.error = "Please fill it"
                else -> {
                    if (isPalindrom(text)) {
                        Toast.makeText(this, "Is Palindrome", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Isn't Palindrome", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.btnNext.setOnClickListener {
            val name = binding.tietName.text.toString()
            when {
                name.isEmpty() -> binding.tietName.error = "Please fill it"
                else -> startActivity(Intent(this, SecondScreenActivity::class.java).putExtra("name", name))
            }
        }
    }

    private fun isPalindrom(text: String) : Boolean {
        val builder = StringBuilder(text)
        val check = builder.reverse().toString()
        return text.equals(check, true)
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
}