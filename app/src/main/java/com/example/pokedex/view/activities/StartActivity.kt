package com.example.pokedex.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pokedex.DarkModeUtil
import com.example.pokedex.R

class StartActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        val buttonGet: Button = findViewById(R.id.buttonGet)
        buttonGet.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        if (DarkModeUtil.isDarkMode) {
            setTheme(R.drawable.bg_dark)
        } else setTheme(R.drawable.bg_default)
    }
}