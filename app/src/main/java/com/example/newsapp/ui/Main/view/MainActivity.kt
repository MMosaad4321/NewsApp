package com.example.newsapp.ui.Main.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Common.BaseActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.Main.ViewModel.MainViewModel
import com.example.newsapp.ui.newsDetails.DetailsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var binding: ActivityMainBinding
    lateinit var newsAdapter: NewsAdapter
    private var bottomNavigationState = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        sharedPreferences = getPreferences(0)
        sharedPreferencesEditor = sharedPreferences.edit()
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this)
        initRecyclerView(viewModel, bottomNavigationState)

        viewModel.articlesList.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.newsProgressBar.visibility = View.GONE
                newsAdapter.notifyDataSetChanged()
            }

        })

        viewModel.navigateToDetails.observe(this, Observer {
            navigate(DetailsActivity::class.java, it)
            //startActivity(Intent(this, DetailsActivity::class.java).putExtra("url",it))
        })
        viewModel.shareNews.observe(this, Observer {

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, it);
            startActivity(Intent.createChooser(intent, "Share News"));
        })
        viewModel.favoriteNews.observe(this, Observer {

            if (sharedPreferences.contains(it.url)) {
                sharedPreferencesEditor.remove(it.url).apply()
                viewModel.deleteNews(it)
            } else {
                sharedPreferencesEditor.putString(it.url, "url").apply()
                viewModel.addNews(it)

            }

        })
        navigationView.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.navigation_news && bottomNavigationState != 1) {
                bottomNavigationState = 1
                initRecyclerView(viewModel, bottomNavigationState)
                return@setOnNavigationItemSelectedListener true
            } else if (it.itemId == R.id.navigation_favourites && bottomNavigationState != 2) {
                bottomNavigationState = 2
                initRecyclerView(viewModel,bottomNavigationState)
                return@setOnNavigationItemSelectedListener true
            }
            return@setOnNavigationItemSelectedListener false
        }


    }

    private fun initRecyclerView(
        viewModel: MainViewModel,
        bottomNavigationState: Int
    ) {
        newsAdapter = NewsAdapter(viewModel, sharedPreferences)
        if (bottomNavigationState == 1)
            viewModel.getDataFromNetwork()
        else
            viewModel.getDataFromRoom()
        binding.newsRecyclerView.adapter = newsAdapter
    }
}