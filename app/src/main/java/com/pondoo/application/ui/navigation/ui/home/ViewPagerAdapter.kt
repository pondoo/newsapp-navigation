package com.pondoo.application.ui.navigation.ui.home


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pondoo.application.R
import com.pondoo.application.model.Article
import kotlinx.android.synthetic.main.viewpager_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ViewPagerAdapter(var context: Context): RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    var list=ArrayList<Article>()
    var formatPattern = "yyyy-MM-dd'T'HH:mm:ss"
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        val image= itemView.ViewPager_image
        val date=itemView.ViewPager_date
        val title = itemView.ViewPager_newsTitle
        val content=itemView.ViewPager_newsDetail
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(list.get(position).urlToImage).into(holder.image)
        holder.content.text=list.get(position).description
        holder.title.text=list.get(position).title
        var formattedTime=setTime(list.get(position).publishedAt)
        holder.date.text=formattedTime
        holder.title.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).url))
            context.startActivity(i)
        })

    }
    fun setTime(date:String?):String{ //Converting local Time
        val simpleDateFormat = SimpleDateFormat(formatPattern)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val outputDate = SimpleDateFormat("HH:mm dd/MM/yyyy")
        outputDate.timeZone = TimeZone.getDefault()
        var fromTime= simpleDateFormat.parse(date)
        var toTime= outputDate.format(fromTime!!)
        return toTime.toString()
    }
    fun updateList(list:List<Article>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}