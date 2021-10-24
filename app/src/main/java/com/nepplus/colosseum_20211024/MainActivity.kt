package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binging : ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binging =  DataBindingUtil.setContentView(this,R.layout.activity_main)

        binging.loginBtn.setOnClickListener {


//          입력한 이메일 / 비번을 데이터 바인딩으로 가져오기.
            val inputEmail = binging.emailEdt.text.toString()
            val inputPw = binging.passwordEdt.text.toString()

//          가져온 이메일 / 비번을 로그로 출력.

            Log.d("입력이메일", inputEmail)
            Log.d("입력비번",inputPw)



        }
    }
}