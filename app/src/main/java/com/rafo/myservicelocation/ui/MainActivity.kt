package com.rafo.myservicelocation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rafo.myservicelocation.R
import com.rafo.myservicelocation.databinding.ActivityMainBinding
import com.rafo.myservicelocation.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openMainFragment()
    }

    private fun openMainFragment() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, MainFragment.newInstance())
            addToBackStack(null)
        }.run { commit() }
    }
}