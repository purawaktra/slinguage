package com.bangkit.slinguage.ui.splass

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.bangkit.slinguage.R

class SplassScreenActivity : AppCompatActivity() {

    companion object{
        private const val TIME_DELAY = 2000L
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splass_screen)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.statusBarColor = Color.TRANSPARENT

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this,
                    SplassNoLoginActivity::class.java)
            )
            finish()

        }, TIME_DELAY)
    }
}