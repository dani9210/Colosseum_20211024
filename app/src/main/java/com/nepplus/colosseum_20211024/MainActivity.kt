package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binging : ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binging =  DataBindingUtil.setContentView(this,R.layout.activity_main)
    }
}