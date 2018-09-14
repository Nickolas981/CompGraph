package ua.kpi.dongumen.compgraph.lab2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View


class LabTwoView : View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var count = 10
    private var degrees: Float = 0F
        get() = 360F / count
    private var colorStep: Int = 0
        get() = 255 / count

    private val paint by lazy {
        Paint().apply { style = Paint.Style.STROKE }
                .apply { color = Color.rgb(0, 0, 0) }
                .apply { strokeWidth = 2f }
    }
    var r = 80
    private val path = Path()


    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        val halfWidth = width.toFloat() / 2
        val halfHeight = height.toFloat() / 2

        initV7()
        c.drawPath(path, paint)
        for (i in 0 until count - 1) {
            c.save()
            paint.color = Color.rgb(colorStep * i, 0, 0)
            c.rotate(degrees, halfWidth, halfHeight)
            c.drawPath(path, paint)
        }
    }

    private fun getPoint(t: Double): Point {
        val a = 3
        val b = 3
        val x = r * (1 + Math.cos((a * t)) + Math.pow(Math.sin(b * t), 2.0)) * Math.cos(t)
        val y = r * (1 + Math.cos((a * t)) + Math.pow(Math.sin(b * t), 2.0)) * Math.sin(t)
        return Point(x, y)
    }


    private fun initV7() {
        val n = 3
        path.reset()

        var t = 0.toDouble()
        val points = mutableListOf<Point>()
        while (t <= n * Math.PI) {
            val point = getPoint(t)
            points.add(point)
            Log.d("Point123", point.toString())
            t += 0.1
        }

        path.moveTo(points[0].x.toFloat() + 500, points[0].y.toFloat() + 400)

        points.forEach {
            path.lineTo(it.x.toFloat() + 500, it.y.toFloat() + 400)
        }
    }

    data class Point(var x: Double, var y: Double)
}
