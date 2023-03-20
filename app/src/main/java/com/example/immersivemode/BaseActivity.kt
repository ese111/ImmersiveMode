package com.example.immersivemode

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    fun actionBarHide() {
        supportActionBar?.hide()
    }

    fun actionBarShow() {
        supportActionBar?.show()
    }
}