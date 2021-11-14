package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.databinding.ActivityEditReplyBinding
import com.nepplus.colosseum_20211024.datas.TopicData
import com.nepplus.colosseum_20211024.utils.ServerUtil
import org.json.JSONObject

class EditReplyActivity : BaseActivity() {

    lateinit var binding : ActivityEditReplyBinding

    lateinit var mTopicData: TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_reply)

    setupEvents()
    setValues()
}


    override fun setupEvents() {

        binding.writeReplyBtn.setOnClickListener {


            val inputContent = binding.replyEdt.text.toString()

            ServerUtil.postRequestWriteReply(mContext,mTopicData.id,inputContent,object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

//                    응답코드 : 200 -> 토스트로 " 의견남기기에 성공해씃ㅂ니다."  화면종료 (이전복귀)

                    val code =jsonObj.getInt("code")

                    if (code == 200) {

                        runOnUiThread {

                            Toast.makeText(mContext, "의견 남기기에 성공했습니다", Toast.LENGTH_SHORT).show()
                            finish()
                        }




                    }


                }


            })

        }

    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic") as TopicData

        binding.topicTitleTxt.text = mTopicData.title
        binding.selectedSideTitleTxt.text = mTopicData.mySide!!.title

    }




}



