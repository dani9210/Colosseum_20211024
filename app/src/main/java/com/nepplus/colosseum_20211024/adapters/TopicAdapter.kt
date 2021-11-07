package com.nepplus.colosseum_20211024.adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.nepplus.colosseum_20211024.datas.TopicData

class TopicAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<TopicData) : ArrayAdapter<TopicData>(mContext,resId,mList) {
}