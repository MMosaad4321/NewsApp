package com.example.newsapp.ui.Main.Splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import kotlinx.coroutines.*

class SplashFragment(private val splashFragmentListener: SplashFragmentListener) : Fragment() {

    interface SplashFragmentListener {
        fun onSplashFinished()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            delay(5000)
            withContext(Dispatchers.Main) {
                splashFragmentListener.onSplashFinished()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


}