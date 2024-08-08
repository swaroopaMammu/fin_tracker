package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.expensetracker.databinding.ActivitySplashScreenBinding
import com.example.expensetracker.login.ui.LoginScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    private var binding : ActivitySplashScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            delay(2000)
            val intent = Intent(this@SplashScreen,LoginScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}