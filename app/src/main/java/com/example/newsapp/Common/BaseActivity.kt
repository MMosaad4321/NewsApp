package com.example.newsapp.Common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.Utilities.URL_KEY

open class BaseActivity : AppCompatActivity()  {

    open fun navigate(destination : Class<*>) {
        val intent = Intent(this , destination);
        startActivity(intent)
    }

    open fun navigate(destination : Class<*>, parameter : String ) {
        val intent = Intent(this , destination)
        intent.putExtra(URL_KEY,parameter)
        startActivity(intent)
    }
}