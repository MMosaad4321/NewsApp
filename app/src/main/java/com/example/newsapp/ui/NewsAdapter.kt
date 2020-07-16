package com.example.newsapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article


class NewsAdapter(var newsArrayList: ArrayList<Article>,val context:Context): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_list,parent,false))
    }

    override fun getItemCount(): Int {
        return newsArrayList.size
    }
    fun updateList(newNewsArrayList : ArrayList<Article> ){
        newsArrayList = newNewsArrayList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.titleTextView.text = newsArrayList[position].title
        holder.nameTextView.text  =  newsArrayList[position].source!!.name
        holder.dateTextView.text =newsArrayList[position].publishedAt

        //TODO: Apply extension function here
        Glide.with(context).load(newsArrayList[position].urlToImage).into(holder.newsImage)

        //TODO: Apply view Model here
        holder.newsImage.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(newsArrayList[position].url))
            context.startActivity(browserIntent)
        }
        holder.descriptionTextView.text = newsArrayList[position].description

    }
    inner class NewsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)
        val newsImage = itemView.findViewById<ImageView>(R.id.newsImage)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)



    }
}