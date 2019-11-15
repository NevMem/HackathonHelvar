package com.nevmem.helvarapp

import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.nevmem.helvarapp.utils.delayedOnUI
import com.nevmem.helvarapp.view.Dot
import kotlinx.android.synthetic.main.pswd_activity.*

class PswdActivity : AppCompatActivity() {
    companion object {
        const val appearanceDuration = 1200L
    }

    private var blocked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pswd_activity)

        val inflater = LayoutInflater.from(this)

        val groups = ArrayList<LinearLayout>()
        for (i in 0..4) {
            val group =
                inflater.inflate(R.layout.pswd_button_group, pswdGroupAnchor, false) as LinearLayout
            groups.add(group)
        }

        val space = inflater.inflate(R.layout.pswd_space, groups.last(), false)
        groups.last().addView(space)

        for (i in 0..9) {
            val button = inflater.inflate(R.layout.pswd_button, groups[(i - 1) / 3], false) as AppCompatButton
            button.text = i.toString()
            button.setOnClickListener {
                if (!blocked) {
                    proceedDigit(i)
                }
            }
            if (i != 0) {
                groups[(i - 1) / 3].addView(button)
            } else {
                groups.last().addView(button)
            }
        }

        val fingerprint = inflater.inflate(R.layout.pswd_fingerprint, groups.last(), false)
        groups.last().addView(fingerprint)

        groups.forEach {
            it.alpha = 0f
            val animator = ValueAnimator.ofFloat(0f, 1f)
            animator.duration = appearanceDuration
            animator.addUpdateListener { _ ->
                it.alpha = animator.animatedValue as Float
            }
            animator.start()

            pswdGroupAnchor.addView(it)
        }
    }

    private val list = ArrayList<Int>()
    private fun proceedDigit(digit: Int) {
        list.add(digit)
        dots.addView(Dot(this))
        if (list.size == 4) {
            blocked = true
            delayedOnUI(100) {
                gotoMainPage()
                list.clear()
            }
        }
    }

    private fun gotoMainPage() {
        startActivity(
            Intent(this, StartActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}