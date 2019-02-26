package com.greenhacker.greenhackeronlinetraining.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button

import com.greenhacker.greenhackeronlinetraining.R
import com.greenhacker.greenhackeronlinetraining.utils.MyBounceInterpolator

class OrderActivity : AppCompatActivity() {
    internal lateinit var btorOrder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        btorOrder = findViewById(R.id.btorOrder)
        btorOrder.setOnClickListener { didTapButton(btorOrder) }
    }

    fun didTapButton(view: View) {
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator

        view.startAnimation(myAnim)
    }
}
