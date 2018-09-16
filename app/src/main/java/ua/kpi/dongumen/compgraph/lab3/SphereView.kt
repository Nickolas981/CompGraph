package ua.kpi.dongumen.compgraph.lab3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class SphereView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var rotation = 0.0
    var distance = 800.0
    var scale = 1f


    private var dotSize = 5
    var offsetX = 0f
    var offsetY = 0f

    private val primaryPaint by lazy {
        Paint().apply { style = Paint.Style.FILL }
                .apply { color = Color.rgb(0, 255, 0) }
                .apply { strokeWidth = 2f }
    }

    private val hintPaint by lazy {
        Paint().apply { style = Paint.Style.FILL }
                .apply { color = Color.rgb(100, 100, 100) }
                .apply { strokeWidth = 2f }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        render(canvas!!)
    }


    private fun rotateX(point: Point3D, radians: Double) {
        val y = point.y
        point.y = (y * Math.cos(radians)) + (point.z * Math.sin(radians) * -1.0)
        point.z = (y * Math.sin(radians)) + (point.z * Math.cos(radians))
    }

    private fun rotateY(point: Point3D, radians: Double) {
        val x = point.x
        point.x = (x * Math.cos(radians)) + (point.z * Math.sin(radians) * -1.0)
        point.z = (x * Math.sin(radians)) + (point.z * Math.cos(radians))
    }

    private fun rotateZ(point: Point3D, radians: Double) {
        val x = point.x
        point.x = (x * Math.cos(radians)) + (point.y * Math.sin(radians) * -1.0)
        point.y = (x * Math.sin(radians)) + (point.y * Math.cos(radians))
    }

    private fun projection(xy: Double, z: Double, xyOffset: Double, zOffset: Double, distance: Double): Double {
        return ((distance * xy) / (z - zOffset)) + xyOffset
    }

    private fun render(ctx: Canvas) {
        val width = ctx.width
        val height = ctx.height
        distance = ((width + height) / 4).toDouble()
        dotSize = (distance / 100).toInt()
        var x: Double
        var y: Double

        val p = Point3D()

        sphere.point.forEach {

            p.x = it.x
            p.y = it.y
            p.z = it.z

            rotateX(p, rotation)
            rotateY(p, rotation)
            rotateZ(p, rotation)

            x = projection(p.x, p.z, width / 2.0, 100.0, distance * scale)
            y = projection(p.y, p.z, height / 2.0, 100.0, distance * scale)



            if ((x + offsetX >= 0) && (x + offsetX < width)) {
                if ((y + offsetY >= 0) && (y + offsetY < height)) {
                    if (p.z < 0) {
                        drawBackPoint(ctx, x + offsetX, y + offsetY)
                    } else {
                        Companion.drawPoint(this, ctx, x + offsetX, y + offsetY)
                    }
                }
            }

            moveToDefaultSize()
            moveToCenter()
        }
    }

    private fun moveToDefaultSize() {
        if (scale > 1) {
            scale -= 0.00001.toFloat()
        } else if (scale < 1) {
            scale += 0.00001.toFloat()
        }
    }

    private fun moveToCenter() {
        if (offsetX > 0) {
            offsetX -= 0.01.toFloat()
        } else if (offsetX < 0) {
            offsetX += 0.01.toFloat()
        }
        if (offsetY > 0) {
            offsetY -= 0.01.toFloat()
        } else if (offsetY < 0) {
            offsetY += 0.01.toFloat()
        }
    }

    private fun drawBackPoint(ctx: Canvas, x: Double, y: Double) {
        ctx.drawCircle(x.toFloat(), y.toFloat(), (dotSize * 0.7).toFloat() * scale / 2, hintPaint)
    }

    companion object {
        val sphere = Sphere3D(50.0)
        fun drawPoint(sphereView: SphereView, ctx: Canvas, x: Double, y: Double) {
            ctx.drawCircle(x.toFloat(), y.toFloat(), sphereView.dotSize.toFloat() * sphereView.scale, sphereView.primaryPaint)
        }
    }
}