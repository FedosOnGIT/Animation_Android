package com.example.animation.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.animation.R

class RobotView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var image: Drawable

    init {
        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RobotView)
        try {
            image = ResourcesCompat.getDrawable(
                context.resources,
                attributes.getResourceId(R.styleable.RobotView_image, 0),
                null
            )!!
        } finally {
            attributes.recycle()
        }
    }

    class Robot(
        var positionX: Int,
        var positionY: Int
    )

    private val robot = Robot(0, 0)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        robot.positionY = height;
    }

    private fun getReflect(d: Drawable) : Drawable {
        val array = arrayOf(d)
        return object : LayerDrawable(array) {
            override fun draw(canvas: Canvas) {
                canvas.save()
                canvas.scale(-1f, 1f, width / 2f, height / 2f)
                super.draw(canvas)
                canvas.restore()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        image.setBounds(
            robot.positionX,
            robot.positionY - 150,
            robot.positionX + 120,
            robot.positionY
        )
        robot.positionX++
        image.draw(canvas)
        if (robot.positionX == width) {
            image = getReflect(image)
            robot.positionX = -120
        }
        invalidate()
    }
}