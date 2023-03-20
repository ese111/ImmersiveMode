package com.example.immersivemode

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.immersivemode.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val binding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = requireActivity().window
        val activity = requireActivity() as AppCompatActivity
        activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController = WindowCompat.getInsetsController(requireActivity().window, window.decorView)
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            window.decorView.setOnApplyWindowInsetsListener { _, windowInsets ->
                if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                    || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())
                ) {
                    binding.toggleFullscreenButton.setOnClickListener {
                        window.statusBarColor = Color.TRANSPARENT
                        val lFlags = window.decorView.systemUiVisibility
                        window.decorView.systemUiVisibility = lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                        binding.apply {
                            appbar.visibility = View.GONE
                            bottomNavigation.visibility = View.GONE
                        }
                    }
                } else {
                    binding.toggleFullscreenButton.setOnClickListener {
                        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.purple_500)
                        val lFlags = window.decorView.systemUiVisibility
                        window.decorView.systemUiVisibility = lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                        binding.apply {
                            appbar.visibility = View.VISIBLE
                            bottomNavigation.visibility = View.VISIBLE
                        }
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
                    binding.apply {
                        appbar.visibility = View.GONE
                        bottomNavigation.visibility = View.GONE
                    }
                } else {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    binding.apply {
                        appbar.visibility = View.VISIBLE
                        bottomNavigation.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}