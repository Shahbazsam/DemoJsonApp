package com.example.demojsonapp

import android.app.Application
import com.example.demojsonapp.data.DefaultContainer
import com.example.demojsonapp.data.ProductAppContainer


class ProductApplication : Application(){
    lateinit var container : ProductAppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer()
    }
}