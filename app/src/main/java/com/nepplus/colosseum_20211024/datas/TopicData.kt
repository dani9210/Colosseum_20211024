package com.nepplus.colosseum_20211024.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData (
    var id: Int,
    var title: String,
    var imageURL: String): Serializable {

//    의견 (댓글) 갯수 변수

    var replyCount = 0

//    선택 진영 " 목록"

    var sideList = ArrayList<SideData>()




//    토픽데이터 만들때는 빈 괄호 TopicDta() 형태도 지원하자.

//    다른 형태의 생성자 (보조 생성자) 도 추가 지원.ㅏ


    constructor() : this(0,"제목없음","주소없음")

    companion object{

//        매번 파싱 코드를 화면마다 짜기가 번거롭다.
//        TopicData의 기능으로 -> 토론에 대한 내용을 갖고있는 JsonObj를 넣으면
//         => 파싱을 진행해서-> TopicData형태로 변화해주는 기능.

//        다른화면에서는 jsonObj만 파싱해서 => 변환 기능으로 활용만.



        fun getTopicDataFromJSON(jsonObj:JSONObject ) : TopicData{

            val topicData = TopicData()

            topicData.id = jsonObj.getInt("id")
            topicData.title = jsonObj.getString("title")
            topicData.imageURL = jsonObj.getString("img_url")

            topicData.replyCount = jsonObj.getInt("reply_count")

//            jsonObj 내부에 sides JSONArray가 있다. => 파싱 : SideData 목록에 추가

            val sidesArr = jsonObj.getJSONArray("sides")

            for (i in 0 until sidesArr.length()){

                val sideObj = sidesArr.getJSONObject(i)
                val sideData = SideData.getSideDataFromJSOn(sideObj)

                topicData.sideList.add(sideData)


            }


            return topicData

        }


    }
}