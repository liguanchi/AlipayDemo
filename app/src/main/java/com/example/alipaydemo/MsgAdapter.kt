package com.example.wechat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alipaydemo.LeftViewHolder
import com.example.alipaydemo.MsgViewHolder
import com.example.alipaydemo.R
import com.example.alipaydemo.RightViewHolder
import com.example.ninepatchdemo.Msg
import kotlin.math.log


class MsgAdapter(private var msgList: List<Msg>) : RecyclerView.Adapter<MsgViewHolder>() {
    //    inner class LeftViewHolder(view: View) : MsgViewHolder(view){
//        val leftMsg: TextView = view.findViewById(R.id.LeftMsg)
//        val leftPicture: ImageView = view.findViewById(R.id.left_imageView)
//    }
//
//    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val rightMsg: TextView = view.findViewById(R.id.rightMsg)
//        val rightPicture: ImageView = view.findViewById(R.id.right_imageView)
//    }
    //在该方法中返回当前消息所对应的消息类型
    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == Msg.TYPE_LEFT) {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item, parent, false)
            LeftViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item, parent, false)
            val viewHolder = RightViewHolder(view)
            //设置长按事件监听器
            viewHolder.itemView.setOnLongClickListener {

                msgList -= msgList[viewHolder.adapterPosition]

                //列表刷新
                notifyItemRemoved(viewHolder.adapterPosition)
//                notifyItemRangeChanged(rightViewHolder.adapterPosition,1)
                true
            }
            viewHolder
        }


    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        val msg = msgList[position]
        when (holder) {
            is LeftViewHolder -> {
                holder.leftMsg.text = msg.content
                holder.leftPicture.setImageResource(msg.MsgImageView)
            }
            is RightViewHolder -> {
                holder.rightMsg.text = msg.content
                holder.rightPicture.setImageResource(msg.MsgImageView)
            }
        }


    }

    override fun getItemCount() = msgList.size

    fun getList() = msgList
}