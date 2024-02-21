package com.example.saverx.app.animations

import android.view.View
import android.view.animation.AnimationUtils
import com.example.saverx.R

class AnimationHelper {
    fun startSlideUpAnimForEditText(view: View) {
        val slideUp1 = AnimationUtils.loadAnimation(view.context, R.anim.slide_up1)
        view.startAnimation(slideUp1)
    }

    fun startSlideUpAnimForTextView(view: View) {
        val slideUp0 = AnimationUtils.loadAnimation(view.context, R.anim.slide_up0)
        view.startAnimation(slideUp0)
    }

}