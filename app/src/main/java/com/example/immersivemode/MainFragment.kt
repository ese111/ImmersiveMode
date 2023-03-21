package com.example.immersivemode

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.example.immersivemode.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val binding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    private val window by lazy {
        requireActivity().window.apply {
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.purple_500)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setNewSystemUiBindAction(view)
        } else {
            setSystemUiBindAction()
        }
    }
    @RequiresApi(Build.VERSION_CODES.R)
    private fun setNewSystemUiBindAction(view: View) {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        window.decorView.setOnApplyWindowInsetsListener { _, windowInsets ->
            if (windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())
                || windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
            ) {
                binding.toggleFullscreenButton.setOnClickListener {
                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                    binding.apply {
                        appbar.visibility = View.GONE
                        bottomNavigation.visibility = View.GONE
                    }
                }
            } else {
                binding.toggleFullscreenButton.setOnClickListener {
                    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                    binding.apply {
                        appbar.visibility = View.VISIBLE
                        bottomNavigation.visibility = View.VISIBLE
                    }
                }
            }
            view.onApplyWindowInsets(windowInsets)
        }
    }
     @Deprecated("Android 29")
    private fun setSystemUiBindAction() {
        if (window.decorView.systemUiVisibility != (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)) {
            binding.toggleFullscreenButton.setOnClickListener {
                window.decorView.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // 가장 자리 스와이프 시 발동, 다만 앱에서는 인지 못함
                            or View.SYSTEM_UI_FLAG_FULLSCREEN // 풀 스크린 모드
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) // 하단 네비게이션 바 숨기기 플래그
                binding.apply {
                    appbar.visibility = View.GONE
                    bottomNavigation.visibility = View.GONE
                }
            }
        } else {
            binding.toggleFullscreenButton.setOnClickListener {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                binding.apply {
                    appbar.visibility = View.VISIBLE
                    bottomNavigation.visibility = View.VISIBLE
                }
            }
        }
    }
}