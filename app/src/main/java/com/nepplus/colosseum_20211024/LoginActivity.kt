package com.nepplus.colosseum_20211024

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.databinding.ActivityLoginBinding
import com.nepplus.colosseum_20211024.utils.ContextUtil
import com.nepplus.colosseum_20211024.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    lateinit var binging: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binging = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()


    }

    override fun setupEvents() {


        binging.autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->

//            isChecked : 클릭되어서 변경된 최종 상태값 을 알려준다.
//             ContextUtil에 변경된 값 저장.

            ContextUtil.setAutoLogin(mContext, isChecked)


        }

        binging.signUpBtn.setOnClickListener {

            val myintent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myintent)


        }



        binging.loginBtn.setOnClickListener {


//          입력한 이메일 / 비번을 데이터 바인딩으로 가져오기.

            val inputEmail = binging.emailEdt.text.toString()
            val inputPw = binging.passwordEdt.text.toString()

//          가져온 이메일 / 비번을 로그로 출력.

            Log.d("입력이메일", inputEmail)
            Log.d("입력비번", inputPw)

//            서버의 로그인 기능에 전달.

            ServerUtil.postRequestLogin(
                inputEmail,
                inputPw,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

//                    화면단에서 jsonObj 분석 -> 상황에 맞는 UI 처리.

                        val code = jsonObj.getInt("code")
//                    로그인 성공시 -> 성공 토스트
//                    실패시 -> 왜 실패했는지 서버가 알려주는대로 토스트

                        if (code == 200) {

//                            응용문제. 로그인 한사람의 닉네임 추출 - >  "~~님, 환영합니다!"

                            val dataObj = jsonObj.getJSONObject("data")
                            val userObj = dataObj.getJSONObject("user")
                            val nickname = userObj.getString("nick_name")

//                            내 정보를 인증하는 데이터 : 토큰 추출

                            val token = dataObj.getString("token")

//                            Sharedpreferences 활용 저장해두자. => 필요할때 꺼내쓰도록.

                            ContextUtil.setToken(mContext, token)


                            runOnUiThread {
                                Toast.makeText(
                                    mContext,
                                    "${nickname}님,환영합니다!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val myIntent = Intent(mContext, MainActivity::class.java)
                                startActivity(myIntent)
                                finish()

                            }


                        } else {

//                            서버가 알려주는 로그인 실패 사유 파싱-> 토스트

                            val message = jsonObj.getString("message")


                            runOnUiThread {

                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }


                        }


                    }


                })


        }

    }

    override fun setValues() {

        binging.autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)


    }


}