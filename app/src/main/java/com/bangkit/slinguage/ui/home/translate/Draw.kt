package com.bangkit.slinguage.ui.home.translate

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class Draw(context: Context, var react: Rect, var text: String) : View(context) {

    lateinit var paint: Paint
    lateinit var textPaint: Paint

    init {
        init()
    }

    private fun init() {
        paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 3f
        paint.style = Paint.Style.STROKE

        textPaint = Paint()
        textPaint.color = Color.RED
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 20f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(text, react.centerX().toFloat(), react.centerY().toFloat(), textPaint)
        canvas.drawRect(react.left.toFloat(), react.top.toFloat(), react.right.toFloat(), react.bottom.toFloat(), paint)
    }
}