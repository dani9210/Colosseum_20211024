package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.adapters.TopicAdapter
import com.nepplus.colosseum_20211024.databinding.ActivityLoginBinding
import com.nepplus.colosseum_20211024.databinding.ActivityMainBinding
import com.nepplus.colosseum_20211024.datas.TopicData
import com.nepplus.colosseum_20211024.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {


    lateinit var binding: ActivityMainBinding

    lateinit var mTopicAdapter: TopicAdapter

    val mTopicList = ArrayList<TopicData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

    }

    override fun setValues() {

//        /v2/main_/main_info API가 토론 주제 목록을 내려줌.
//        서버 호출 => 파싱해서, mTopicList를 채워주자.
        getTopicListFromServer()


        mTopicAdapter = TopicAdapter(mComtext,R.layout.topic_list_item, mTopicList)
        binding.topicListView.adapter = mTopicAdapter



    }



    fun getTopicListFromServer() {

        ServerUtil.getRequestMainInfo(mComtext, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {


                val dataObj = jsonObj.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")

//                0번째 주제 ~ topicsArr 갯수 직전까지를 반복.
//                5개 주제 : 0 ~ 4 번주제까지. (5개)

                for( index in 0 until topicsArr.length() ){

//                    [   ]  안에 있는 {  } 를 순서대로  파싱하자.

                    val topicObj = topicsArr.getJSONObject(index)

//                    topicObj는 토론 주제에 필요한 데이터를 들고있다.
//                    TopicData() 형태로 변환해주자.  => 목록에 추가해주자.

                    val topicData = TopicData()
                    topicData.id = topicObj.getInt("id")
                    topicData.title = topicObj.getString("title")
                    topicData.imageURL = topicObj.getString("img_url")

//                    만들어진 topicData를 목록에 추가.
                    mTopicList.add(topicData)


                }


//                for문이 끝나면, mTopicList에 모든 토론 주제가 추가된 상태다.
//                어댑터가 변경사항을 감지하도록 처리하자. => 내용 반영 : UI변경 ( 백그라운드)

                runOnUiThread{

                    mTopicAdapter.notifyDataSetChanged()

                }



            }


        })




    }


}