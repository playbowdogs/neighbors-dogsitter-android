package com.playbowdogs.neighbors_dogsitter_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.playbowdogs.neighbors_dogsitter_android.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        ViewModelProvider(this).get(SharedViewModel::class.java)
    }
}