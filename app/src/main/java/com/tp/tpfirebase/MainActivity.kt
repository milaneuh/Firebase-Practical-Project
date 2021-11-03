package com.tp.tpfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tp.tpfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bt.setOnClickListener {
            val intent: Intent = Intent(applicationContext,FirebaseExampleActivity::class.java)
            startActivity(intent)
        }
    }
}