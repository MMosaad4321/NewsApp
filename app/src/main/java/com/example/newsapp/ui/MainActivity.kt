package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.models.Article
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val newsAdapter =  NewsAdapter(ArrayList(),this)
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         var binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding


         //TODO: apply KTX
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getNews()
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.newsRecyclerView.adapter = newsAdapter
        viewModel.articlesList.observe(this, Observer {
            newsAdapter.updateList(it as ArrayList<Article>)
        })

    }
}