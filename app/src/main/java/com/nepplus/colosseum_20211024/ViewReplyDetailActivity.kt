package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.databinding.ActivityViewReplyDetailBinding


class ViewReplyDetailActivity : BaseActivity() {

    lateinit var binding : ActivityViewReplyDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()

    }



    override fun setupEvents() {

    }

    override fun setValues() {

    }


}