package com.example.newsapp.ui.Main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.Utilities.replaceFragment
import com.example.newsapp.ui.Main.Favourite.view.HeadLineNewsLocalFragment
import com.example.newsapp.ui.Main.HeadLines.view.HeadLinesNewsFragment
import com.example.newsapp.ui.Main.Splash.SplashFragment
import com.example.newsapp.ui.Main.login.LoginFragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity(), SplashFragment.SplashFragmentListener, LoginFragment.LoginFragmentListener {

    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView.visibility = View.GONE

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, SplashFragment(this))
            .commit()

        navigationView.setOnNavigationItemSelectedListener {
            displayFragment(it.itemId)
            return@setOnNavigationItemSelectedListener false
        }


    }

    private fun displayFragment(id : Int) {
        when(id) {
            R.id.navigation_news -> {
                mainViewModel.bottomNavigationState = 1
                supportFragmentManager.replaceFragment(HeadLinesNewsFragment())
            }

            R.id.navigation_favourites -> {
                mainViewModel.bottomNavigationState = 2
                supportFragmentManager.replaceFragment(HeadLineNewsLocalFragment())
            }

        }
    }

    override fun onSplashFinished() {

        if (mainViewModel.isLoggedIn) {
            // already signed in

            navigationView.visibility = View.VISIBLE
            supportFragmentManager.replaceFragment(HeadLinesNewsFragment())
        } else {
            // not signed in
            supportFragmentManager.replaceFragment(LoginFragment(this))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.signout_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out){
            AuthUI.getInstance().signOut(this)
            supportFragmentManager.replaceFragment(LoginFragment(this))
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onUserSignedIn() {
        navigationView.visibility = View.VISIBLE
        supportFragmentManager.replaceFragment(HeadLinesNewsFragment())

    }

    override fun onStart() {
        super.onStart()
        val auth = FirebaseAuth.getInstance()
        mainViewModel.isLoggedIn = auth.currentUser != null
    }

}