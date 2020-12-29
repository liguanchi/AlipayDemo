package com.example.alipaydemo

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ninepatchdemo.Msg
import com.example.wechat.MsgAdapter
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.chat_bottom.*
import java.util.ArrayList

class ChatActivity : AppCompatActivity() {
    private var msgList = ArrayList<Msg>()
    private lateinit var adapter: MsgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.hide()//去掉标题

        fullScreen(this)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        //数据初始化
        initMsgData()
        //设置线性布局管理器到控件
        val layoutManager = LinearLayoutManager(this)
        //判断adapter是否已经初始化
        RecyclerViewTest.layoutManager = layoutManager
        if (!::adapter.isInitialized) {
            adapter = MsgAdapter(msgList)
        }


        RecyclerViewTest.adapter = adapter

//        滚动到最新数据的位置上
        RecyclerViewTest.scrollToPosition(msgList.size - 1)

        msgSend.setOnClickListener {
            val text: String = msgText.text.toString()

            if (text.isNotEmpty()) {
                val list: List<Msg> = adapter.getList()
                if (msgList.size != list.size) msgList = list as ArrayList<Msg>
                //增加数据到数据源
                val msg = Msg(text, R.drawable.miabao, Msg.TYPE_RIGHT)
                msgList.add(msg)
                //通知适配器增加了数据
                adapter.notifyItemInserted(msgList.size - 1)
                //滚动到最新数据的位置上
                RecyclerViewTest.scrollToPosition(msgList.size - 1)
                //清空输入框的内容
                msgText.setText("")
            }
        }


        rootLayout.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (oldBottom != -1 && oldBottom > bottom) {
                RecyclerViewTest.requestLayout()
                RecyclerViewTest.post { RecyclerViewTest.scrollToPosition(msgList.size - 1) }
            }
        }



    }


    private fun initMsgData() {
        repeat(1)
        {
            msgList.add(Msg("今晚打王者么咯",R.drawable.img_1,0))
        }
    }



    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private fun fullScreen(activity: Activity) {
        run {

            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            val window = activity.window
            val decorView = window.decorView
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }


}