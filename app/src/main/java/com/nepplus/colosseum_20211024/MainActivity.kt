package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.databinding.ActivityLoginBinding
import com.nepplus.colosseum_20211024.databinding.ActivityMainBinding
import com.nepplus.colosseum_20211024.utils.ServerUtil

class MainActivity : BaseActivity() {


    lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

    }

    override fun setValues() {

//        연습 - 내 정보 받아오기 소출 =>  닉네임 파싱, 텍스트뷰에 반영





    }


}