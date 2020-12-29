package com.example.ninepatchdemo

class Msg(val content:String,val MsgImageView:Int,val type:Int) {
    companion object{
        const val TYPE_LEFT = 0
        const val TYPE_RIGHT = 1
    }
}