package com.example.immersivemode

import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.immersivemode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (VERSION.SDK_INT >= VERSION_CODES.R) {
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
                if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                    || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())
                ) {
                    binding.toggleFullscreenButton.setOnClickListener {
                        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                        supportActionBar?.hide()
                    }
                } else {
                    binding.toggleFullscreenButton.setOnClickListener {
                        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                        supportActionBar?.show()
                    }
                }
                view.onApplyWindowInsets(windowInsets)
            }
        } else {
            binding.toggleFullscreenButton.setOnClickListener {
                if(window.decorView.systemUiVisibility != (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)) {
                    window.decorView.systemUiVisibility =
                        (View.SYSTEM_UI_FLAG_IMMERSIVE // 가장 자리 스와이프 시 발동, 다만 앱에서는 인지 못함
                                or View.SYSTEM_UI_FLAG_FULLSCREEN // 풀 스크린 모드
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) // 하단 네비게이션 바 숨기기 플래그
                    supportActionBar?.hide()
                } else {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    supportActionBar?.show()
                }
            }
        }
    }
}