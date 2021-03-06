package com.nepplus.colosseum_20211024

import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.databinding.ActivitySignUpBinding
import com.nepplus.colosseum_20211024.utils.ServerUtil
import org.json.JSONObject
import java.util.regex.Pattern

class SignUpActivity : BaseActivity() {


    lateinit var binding: ActivitySignUpBinding

//    이메일 중복검사 통과 여부 저장 변수.

    var isEmailOk = false  // 기본값 : 통과 X. 그래서 false. => 자료형 자동으로 Boolean으로 설정.
    var isNicknameOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()

    }


    override fun setupEvents() {

        binding.nickNameEdt.addTextChangedListener {


            binding.nicknameCheckResultTxt.text = "닉네임 중복 검사를 해주세요."
            isNicknameOk = false

        }



        binding.checkNicknameBtn.setOnClickListener {

            val inputNickname = binding.nickNameEdt.text.toString()

            ServerUtil.getRequestDuplCheck(
                "NICK_NAME",
                inputNickname,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {


                        runOnUiThread {


                            val code = jsonObj.getInt("code")

                            if (code == 200) {

                                binding.nicknameCheckResultTxt.text = "사용해도 좋습니다."
                                isNicknameOk = true


                            } else {

                                binding.nicknameCheckResultTxt.text = "다른 닉네임을 입력하고, 다시검사해주세요."
                                isNicknameOk = false


                            }
                        }


                    }


                })


        }


        binding.emailEdt.addTextChangedListener {

//            val inputContent = it.toString()
//            Log.d("변경된내용",inputContent)

//            이메일이 한글자라도 바뀌면 -> 검사를 다시요구.

            binding.emailCheckResultTxt.text = "이메일 중복 검사를 해주세요."
            isEmailOk = false


        }



        binding.checkEmailBtn.setOnClickListener {

            val inputEmail = binding.emailEdt.text.toString().trim()

            ServerUtil.getRequestDuplCheck(
                "EMAIL",
                inputEmail,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

                        val code = jsonObj.getInt("code")

                        val emailValidation =
                            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

                        val emailPattern = Pattern.matches(emailValidation, inputEmail)



                        runOnUiThread {


                            if (code == 200) {


                                binding.emailCheckResultTxt.text = "사용해도 좋습니다."
                                isEmailOk = true




                                if (emailPattern) {

                                    binding.emailCheckResultTxt.text = "사용해도 좋습니다"

                                    isEmailOk = true


                                } else {

                                    binding.emailCheckResultTxt.text = "이메일 형식으로 입력해주세요."
                                    isEmailOk = false


                                }


                            } else {

                                binding.emailCheckResultTxt.text = "다른 이메일을 입력하고, 다시 검사해주세요."
                                isEmailOk = false


                            }


                        }


                    }


                })


        }



        binding.okBtn.setOnClickListener {


            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.passwordEdt.text.toString()
            val inputNickname = binding.nickNameEdt.text.toString()


//            입력값들이 괜찮은지 먼저 검사. => 전부 통과해야 회원가입 실행.


//            도전과제. 구글링 필요. => 입력한 이메일이, 이메일양식이 맞는지?  aaa@bbb.com 등.체크하기
//            정규 표현식 활용. -> 이메일 양식 검증정규표현식 어떻게 쓸까?


            if (!isNicknameOk) {

                Toast.makeText(mContext, "닉네임 검사를 다시 해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (!isEmailOk) {

                Toast.makeText(mContext, "이메일 확인을 다시 해주세요", Toast.LENGTH_SHORT).show()

//                return : 원래는 함수의 결과를 지정하기 위한 용도.
//                응용 : 결과 뒤로의 함수 내용은 실행되니 않는다. => 이 함수를 강제 종료 시키는 기능.
                return@setOnClickListener

            }


//            입력한 비번이 8글자 이상힌가? 검증

            if (inputPw.length < 8) {

                Toast.makeText(mContext, "비밀번호는 8글자 이상으로 해주세요", Toast.LENGTH_SHORT).show()

                return@setOnClickListener

            }


//            입력 데이터를 => 서버의 회원가입 기능에 요청.=> ServerUtil 함수 활용. => 함수가 아직 없으니 추가로 만들자.

            ServerUtil.putRequestSignUp(
                inputEmail,
                inputPw,
                inputNickname,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

//                  jsonObj 분석 -> UI 반영 코드만 작성.

//                  code : 성공(200) / 실패 (그 외) 여부 .

                        val code = jsonObj.getInt("code")

                        if (code == 200) {

//                      회원가입 성공.=> "~~님, 회원가입을 축하합니다!"

                            val dataObj = jsonObj.getJSONObject("data")
                            val userObj = dataObj.getJSONObject("user")
                            val nickname = userObj.getString("nick_name")

                            runOnUiThread {

                                Toast.makeText(
                                    mContext,
                                    "${nickname}회원가입을 축하합니다!",
                                    Toast.LENGTH_SHORT
                                ).show()
//                          회원가입종료, 로그인 복귀
                                finish()


                            }


                        } else {

//                      회원가입 실패. => 왜? message String에 담겨있을 예정.

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

    }


}


