package com.example.animation.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
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

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(
            super.onSaveInstanceState(),
            robot)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restore = state as SavedState
        super.onRestoreInstanceState(restore.superState)
        robot = restore.robot

    }

    class SavedState : BaseSavedState {
        var robot : Robot
        constructor(superState: Parcelable?, inputRobot: Robot) : super(superState) {
            this.robot = inputRobot
        }

        constructor(superState: Parcel) : super(superState) {
            this.robot = Robot(0, 0)
        }

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeInt(robot.positionX)
            out?.writeInt(robot.positionY)
        }

        val CREATOR: Parcelable.Creator<SavedState?> =
            object : Parcelable.Creator<SavedState?> {
                override fun createFromParcel(input : Parcel): SavedState {
                    return SavedState(input)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls<SavedState>(size)
                }
            }
    }

    class Robot(
        var positionX: Int,
        var positionY: Int
    ) {
        fun move() {
            positionX++
        }
    }

    private var robot = Robot(0, 0)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        robot.positionY = height;
    }

    private fun getReflect(d: Drawable): Drawable {
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
        robot.move()
        image.draw(canvas)
        if (robot.positionX == width) {
            image = getReflect(image)
            robot.positionX = -120
        }
        invalidate()
    }
}