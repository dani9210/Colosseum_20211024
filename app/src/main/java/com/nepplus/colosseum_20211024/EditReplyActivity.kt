package com.nepplus.colosseum_20211024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum_20211024.databinding.ActivityEditReplyBinding

class EditReplyActivity : BaseActivity() { override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_reply)

    setupEvents()
    setValues()
}

    lateinit var binding : ActivityEditReplyBinding
    override fun setupEvents() {

    }

    override fun setValues() {

    }




}



