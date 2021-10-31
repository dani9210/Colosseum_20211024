package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {


    val mComtext = this


    abstract fun  setupEvents()
    abstract fun  setValues()



}