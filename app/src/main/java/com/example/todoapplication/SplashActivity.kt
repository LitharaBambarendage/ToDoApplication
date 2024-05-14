package com.example.todoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val i = Intent(this@SplashActivity, home_layout::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            startActivity(i)
            finish()
        }
    }
}
