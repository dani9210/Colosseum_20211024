package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.adapters.ReReplyAdapter
import com.nepplus.colosseum_20211024.databinding.ActivityViewReplyDetailBinding
import com.nepplus.colosseum_20211024.datas.ReplyData
import com.nepplus.colosseum_20211024.utils.ServerUtil
import org.json.JSONObject


class ViewReplyDetailActivity : BaseActivity() {

    lateinit var binding : ActivityViewReplyDetailBinding

    lateinit var mReplyData : ReplyData


    val maReReplayList = ArrayList<ReplyData>()

    lateinit var mReREplyadapter : ReReplyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()

    }



    override fun setupEvents() {

    }

    override fun setValues() {

        mReplyData = intent.getSerializableExtra("reply") as ReplyData


        binding.writerNicknameTxt.text = mReplyData.user.nickname
        binding.selectedSideTitleTxt.text = mReplyData.selectedSide.title
        binding.contextTxt.text = mReplyData.content

        getReplyDetailFromServer()

        mReREplyadapter = ReReplyAdapter(mContext,R.layout.re_reply_list_item,maReReplayList)
        binding.replyListView.adapter = mReREplyadapter



    }


    fun getReplyDetailFromServer() {

        ServerUtil.getRequestReplyDetail(mContext,mReplyData.id, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val replyObj = dataObj.getJSONObject("reply")
                val repliesArr = replyObj.getJSONArray("replies")

                for(i in 0 until repliesArr.length()){

//                    위치에 맞는 JSONObject  {  }추출 -> ReplyData로 변환 -> 대댓글목록에 추가

                    maReReplayList.add( ReplyData.getReplayDataFromJson( repliesArr.getJSONObject(i)))



                }


                runOnUiThread {

                    mReREplyadapter.notifyDataSetChanged()

                }


            }


        })


    }



}