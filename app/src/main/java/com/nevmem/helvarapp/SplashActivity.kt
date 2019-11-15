package com.nevmem.helvarapp

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity() {
    companion object {
        const val defaultDelay = 100L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler(Looper.getMainLooper()).postDelayed({
            goToPswd()
        }, defaultDelay)
    }

    private fun goToPswd() {
        startActivity(
            Intent(this, PswdActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this, appIcon, "appIcon").toBundle())
    }
}