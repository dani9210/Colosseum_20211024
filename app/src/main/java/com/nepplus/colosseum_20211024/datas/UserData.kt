package com.nepplus.colosseum_20211024.datas

import org.json.JSONObject

class UserData {

    var id = 0
    var email = ""
    var nickname = ""


    companion object {

        fun getUserdataFromJson(jsonObj : JSONObject) : {


            val userData = UserData()

            userData.id = jsonObj.getInt("id")
            userData.email = jsonObj.getString("email")
            userData.nickname = jsonObj.getString("nick_name")

            return UserData


        }

    }

}