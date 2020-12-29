package com.example.alipaydemo

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_information.*
import java.text.SimpleDateFormat
import java.util.*

class Information : AppCompatActivity() {
    private val newsList = ArrayList<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        supportActionBar?.hide()//去掉标题

        fullScreen(this)
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        initNews()

        val layoutManager = LinearLayoutManager(this)

        NewsRecyclerView.layoutManager = layoutManager

        val adapter = NewsAdapter(newsList)

        NewsRecyclerView.adapter = adapter



        MainPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        MoneyPage.setOnClickListener {
            val intent = Intent(this, Money::class.java)
            startActivity(intent)
            finish()
        }

        MyAppPage.setOnClickListener {
            val intent = Intent(this, MyApp::class.java)
            startActivity(intent)
            finish()
        }

    }

    inner class NewsAdapter(private val newsList: List<News>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val img:ImageView = view.findViewById(R.id.img)
            val title:TextView = view.findViewById(R.id.news_title)
            val context:TextView = view.findViewById(R.id.NewsContext)
            val time:TextView = view.findViewById(R.id.NewsTime)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {
                val intent = Intent(parent.context,ChatActivity::class.java)
                parent.context.startActivity(intent)
            }
            return viewHolder
        }

        override fun getItemCount() = newsList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val news = newsList[position]
            holder.img.setImageResource(news.img)
            holder.title.text = news.title
            holder.context.text = news.context
            holder.time.text = getTime()
        }

    }


    private fun initNews(){
        repeat(5){
            newsList.add(News(R.drawable.shh,"生活号","好医保：仙人掌真的能防辐射吗?其实..."))
            newsList.add(News(R.drawable.mycf,"蚂蚁财富","狂卖30000亿！今年基金会挣翻了"))
            newsList.add(News(R.drawable.img_1,"威~~~","今晚打王者么咯"))
            newsList.add(News(R.drawable.img_3,"池~~~","ok咯"))
            newsList.add(News(R.drawable.jiaoto,"交通出行","超千万人通勤60分钟以上，你呢"))
            newsList.add(News(R.drawable.bei,"花呗","我嗨了"))
        }
    }

    fun getTime(): String {
        val date = Date()
        val dateFormat = SimpleDateFormat("mm:ss", Locale.CHINA)
        return dateFormat.format(date)
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