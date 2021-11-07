package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.nepplus.colosseum_20211024.adapters.ReplyAdapter
import com.nepplus.colosseum_20211024.databinding.ActivityViewTopicDetailBinding
import com.nepplus.colosseum_20211024.datas.ReplyData
import com.nepplus.colosseum_20211024.datas.TopicData
import com.nepplus.colosseum_20211024.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewTopicDetailBinding
    lateinit var mTopicData : TopicData

    val mReplyList = ArrayList<ReplyData>()

    lateinit var mReplyAdapter : ReplyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()
    }



    override fun setupEvents() {

    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic") as TopicData

        binding.topicTitleTxt.text = mTopicData.title

        Glide.with(mContext).load(mTopicData.imageURL).into(binding.topicImg)

        getTopicDetailFromServer()

        mReplyAdapter = ReplyAdapter(mContext,R.layout.reply_list_item,mReplyList)
        binding.replyListView.adapter = mReplyAdapter




    }


    fun getTopicDetailFromServer(){

        ServerUtil.getRequestTopicDetail(mContext, mTopicData.id,"New", object : ServerUtil.JsonResponseHandler {

            override fun onResponse (jsonObj : JSONObject)  {

                //        댓글목록 JSONArray -> 파싱 -> mReplyList의 자료로 추가

                val dataobj = jsonObj.getJSONObject("data")
                val topicObj = dataobj.getJSONObject("topic")

//                topicObj (JSONObject) 를 새 TopicData로 파싱 => 최신정보 반영

                mTopicData = TopicData.getTopicDataFromJson(topicObj)

                val  repliesArr = topicObj.getJSONArray("replies")

                for ( i in 0 until repliesArr.length()) {


                    val replyObj = repliesArr.getJSONObject(i)


//                    JSONObject -> ReplyData 객체로 변환.

                    val replyData = ReplyData( )
                    replyData.id = replyObj.getInt("id")
                    replyData.content = replyObj.getString("content")

                    mReplyList.add(replyData)


                }

//                리스트뷰의 목록에 변경 => 어댑터 새로고침(UI 변경)

                     runOnUiThread {

                         mReplyAdapter.notifyDataSetChanged()
                     }










            }




        })



    }





    }








