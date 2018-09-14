package ua.kpi.dongumen.compgraph.lab3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


class LabThreeView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val radius = 600

    var mRotation = 1.toDouble()
    var distance = 1.toDouble()


    override fun onDraw(canvas: Canvas?) {
        initView(canvas!!)
//        drawPointWithGradient(canvas!!, 700F, 900f,100 )
    }

    private val paint by lazy {
        Paint().apply { style = Paint.Style.FILL }
                .apply { color = Color.rgb(0, 0, 0) }
                .apply { strokeWidth = 2f }
    }
    private val path = Path()


    fun initView(canvas: Canvas) {
        var alpha = 0.toDouble()
        val points = mutableListOf<Point3D>()

        while (alpha <= 6.28) {
            points.add(Point3D())

            points.last().x = Math.cos(alpha) * radius
            points.last().z = Math.sin(alpha) * radius

            alpha += 0.17
        }
        var direction = 1
        while (direction >= -1) {
            var beta = 0.17
            while (beta < 1.445) {

                val radius = Math.cos(beta) * this.radius
                val fixedY = Math.sin(beta) * this.radius * direction

                var alpha = 0.toDouble()
                while (alpha < 6.28) {
                    points.add(Point3D())


                    points.last().x = Math.cos(alpha) * radius
                    points.last().y = fixedY
                    points.last().z = Math.sin(alpha) * radius
                    alpha += 0.17
                }
                beta += 0.17
            }
            direction -= 2
        }


        var i = 0
        while (i < points.size) {
            val point = points[i]


            rotateX(point, mRotation)
            rotateY(point, mRotation)
            rotateZ(point, mRotation)

            x = projection(point.x, point.z, width / 2.0, 100.0, distance).toFloat() - 100
            y = projection(point.y, point.z, height / 2.0, 100.0, distance).toFloat() - 100

            if (x >= 0 && x < width) {
                if (y >= 0 && y < height) {
                    if (point.z < 0) {
                        drawPoint(canvas, x, y, 10.0)
                    } else {
                        drawPointWithGradient(canvas, x, y, 10)
                    }
                }
            }
            i++
        }
    }

    data class Point3D(val something: Boolean = false) {
        var x: Double = 0.toDouble()
        var y: Double = 0.toDouble()
        var z: Double = 0.toDouble()
    }

    fun rotateX(point: Point3D, radians: Double) {
        val y = point.y
        point.y = (y * Math.cos(radians)) + (point.z * Math.sin(radians) * -1.0)
        point.z = (y * Math.sin(radians)) + (point.z * Math.cos(radians))
    }

    fun rotateY(point: Point3D, radians: Double) {
        val x = point.x
        point.x = (x * Math.cos(radians)) + (point.z * Math.sin(radians) * -1.0)
        point.z = (x * Math.sin(radians)) + (point.z * Math.cos(radians))
    }

    fun rotateZ(point: Point3D, radians: Double) {
        val x = point.x
        point.x = (x * Math.cos(radians)) + (point.y * Math.sin(radians) * -1.0)
        point.y = (x * Math.sin(radians)) + (point.y * Math.cos(radians))
    }

    fun projection(xy: Double, z: Double, xyOffset: Double, zOffset: Double, distance: Double): Double {
        return ((distance * xy) / (z - zOffset)) + xyOffset
    }

    fun drawPoint(ctx: Canvas, x: Float, y: Float, size: Double) {
        ctx.drawCircle(x, y, size.toFloat(), paint)
    }

    private fun drawPointWithGradient(ctx: Canvas, x: Float, y: Float, size: Int) {
        ctx.drawCircle(x, y, (size.toFloat() * 0.8).toFloat(), paint)

    }
}