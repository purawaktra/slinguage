package com.bangkit.slinguage.ui.home.translate

import android.content.Context
import android.graphics.*
import android.view.View

//class Draw(context: Context, var react: Rect, var text: String) : View(context) {
//
//    lateinit var paint: Paint
//    lateinit var textPaint: Paint
//
//    init {
//        init()
//    }
//
//    private fun init() {
//        paint = Paint()
//        paint.color = Color.RED
//        paint.strokeWidth = 3f
//        paint.style = Paint.Style.STROKE
//
//        textPaint = Paint()
//        textPaint.color = Color.RED
//        textPaint.style = Paint.Style.FILL
//        textPaint.textSize = 20f
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        canvas.drawText(text, react.centerX().toFloat(), react.centerY().toFloat(), textPaint)
//        canvas.drawRect(react.left.toFloat(), react.top.toFloat(), react.right.toFloat(), react.bottom.toFloat(), paint)
//    }
//}
class Draw(context: Context, var react: RectF, var text: String) : View(context) {

    private  val MAX_FONT_SIZE = 96F
    lateinit var paint: Paint
    lateinit var textPaint: Paint

    init {
        init()
    }

    private fun init() {
        paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 8f
        paint.style = Paint.Style.STROKE

        textPaint = Paint()
        textPaint.color = Color.BLUE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 20f
    }

    private fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)


            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F

            pen.textSize = MAX_FONT_SIZE
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(text, react.centerX().toFloat(), react.centerY().toFloat(), textPaint)
        canvas.drawRect(react, paint)
    }
}
data class DetectionResult(val boundingBox: RectF, val text: String)
