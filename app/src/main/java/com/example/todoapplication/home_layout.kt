package com.example.todoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class home_layout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_layout)

        val button: Button = findViewById(R.id.alltasksbtn)
        button.setOnClickListener {
            // Intent to navigate from FirstActivity to SecondActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
