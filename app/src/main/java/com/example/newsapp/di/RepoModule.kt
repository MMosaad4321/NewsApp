package com.example.newsapp.di

import com.example.newsapp.Common.NewsRepository
import org.koin.dsl.module

val repoModule = module {
    single { NewsRepository() }
}