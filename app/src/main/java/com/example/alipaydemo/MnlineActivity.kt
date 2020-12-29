package com.example.alipay

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.alipaydemo.MainActivity
import com.example.alipaydemo.R
import kotlinx.android.synthetic.main.activity_mnline.*

class MnlineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mnline)

        supportActionBar?.hide()//去掉标题

        fullScreen(this)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        button_login.setOnClickListener {
            val name = edit_phone.text.toString()
            val pad = edit_pwd.text.toString()
            if (name.isEmpty() || pad.isEmpty()) {
                Toast.makeText(this, "请输入账号或密码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (name == "123456" && pad == "123456") {
                val progressDialog =
                    ProgressDialog(this, R.style.Theme_MaterialComponents_Light_Dialog)
                progressDialog.isIndeterminate = true
                progressDialog.setMessage("正在登录")
                progressDialog.show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()


                //获取系统服务中的通知服务，并强制转换类型为通知管理器
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                //兼容性判断
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //创建通知渠道,参数:id ,通知渠道名称，通知的重要等级（默认等级）
                    val channel =
                            NotificationChannel("normal", "测试通道", NotificationManager.IMPORTANCE_HIGH)
                    manager.createNotificationChannel(channel)
                }

                //创建通知，参数：上下文，通知id
                val notification = NotificationCompat.Builder(this, "normal")
                        .setContentTitle("转账到支付宝账户")
                        .setContentText("晓哥已成功向你转了1000万元")
                        .setSmallIcon(R.drawable.alipay)
                        .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.alipay))
                        .build()

                //
                manager.notify(1, notification)
            }else{
                Toast.makeText(this, "请输入正确的账号或密码", Toast.LENGTH_SHORT).show()
            }

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