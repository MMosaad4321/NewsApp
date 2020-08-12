package com.example.newsapp.di

import androidx.lifecycle.ViewModelStoreOwner
import com.example.newsapp.ui.Main.DetailsForSource.ViewModel.SourceDetailsViewModel
import com.example.newsapp.ui.Main.Favourite.ViewModel.HeadLinesLocalViewModel
import com.example.newsapp.ui.Main.HeadLines.ViewModel.HeadLinesViewModel
import com.example.newsapp.ui.Main.HeadLines.view.HeadLinesNewsFragment
import com.example.newsapp.ui.Main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    viewModel { SourceDetailsViewModel() }
    viewModel { HeadLinesLocalViewModel() }
    viewModel { HeadLinesViewModel() }
    viewModel { MainViewModel() }
}