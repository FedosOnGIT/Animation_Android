package com.example.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mAnimAndroid : (ImageView) = findViewById(R.id.robot);
        val drawable: Drawable = mAnimAndroid.drawable
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }
        val text : TextView = findViewById(R.id.text)
        ObjectAnimator.ofFloat(text, View.ALPHA, 0f, 1f).apply {
            repeatCount = Animation.INFINITE
            duration = 4000L
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }
}