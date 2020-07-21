package com.example.newsapp.ui.Main

import android.R.attr
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.newsapp.R
import com.example.newsapp.ui.Main.Favourite.view.HeadLineNewsLocalFragment
import com.example.newsapp.ui.Main.HeadLines.view.HeadLineNewsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager : FragmentManager
    private lateinit var  transaction : FragmentTransaction
    private var bottomNavigationState = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager =supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainer,
            HeadLineNewsFragment()
        )
        transaction.commit()

        navigationView.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.navigation_news && bottomNavigationState != 1) {

                bottomNavigationState = 1
                fragmentManager.replaceFragment(HeadLineNewsFragment())
                return@setOnNavigationItemSelectedListener true

            } else if (it.itemId == R.id.navigation_favourites && bottomNavigationState != 2) {

                bottomNavigationState = 2
                fragmentManager.replaceFragment(HeadLineNewsLocalFragment())
                return@setOnNavigationItemSelectedListener true

            }
            return@setOnNavigationItemSelectedListener false
        }


    }

    private fun FragmentManager.replaceFragment(fragment: Fragment) {
        transaction = this.beginTransaction()
        transaction.replace(R.id.fragmentContainer,fragment)
        transaction.commit()
    }




}