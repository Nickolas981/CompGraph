package ua.kpi.dongumen.compgraph.lab3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


class SphereWirefremeView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var rotation = 0.0
    private var distance = 800.0
    var scale = 1f


    var offsetX = 0f
    var offsetY = 0f

    var lastX = -1.0
    var lastY = -1.0

    private val foregroundPath = Path()
    private val backgroundPath = Path()

    private val dotRotation = Point3D()

    private val primaryPaint by lazy {
        Paint().apply { style = Paint.Style.STROKE }
                .apply { color = Color.rgb(0, 255, 0) }
                .apply { strokeWidth = 2f }
    }

    private val hintPaint by lazy {
        Paint().apply { style = Paint.Style.STROKE }
                .apply { color = Color.rgb(100, 100, 100) }
                .apply { strokeWidth = 2f }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        renderWireframe(canvas!!)
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


    private fun renderWireframe(ctx: Canvas) {

        val width = ctx.width
        val height = ctx.height


        foregroundPath.reset()
        backgroundPath.reset()


        for (i in sphere.vertices.indices) {
            strokeSegment(i, width.toDouble(), height.toDouble())
        }

        var i = 0
        while (i < sphere.slices + 1) {
            var j = 0
            while (j < sphere.rings + 1) {
                strokeSegment(i + j * (sphere.slices + 1), width.toDouble(), height.toDouble())
                j++
            }
            i++
        }

        ctx.drawPath(backgroundPath, hintPaint)
        ctx.drawPath(foregroundPath, primaryPaint)
    }

    private fun strokeSegment(index: Int, width: Double, height: Double) {
        val x: Double
        val y: Double
        val p = sphere.vertices[index]

        rotateX(p, dotRotation.x)
        rotateY(p, dotRotation.y)
        rotateZ(p, dotRotation.z)

        x = projection(p.x, p.z, width / 2.0, 100.0, distance)
        y = projection(p.y, p.z, height / 2.0, 100.0, distance)

        if (lastX == -1.0 && lastY == -1.0) {
            lastX = x
            lastY = y
            return
        }

        if (x >= 0 && x < width && y >= 0 && y < height) {
            if (p.z < 0) {
                backgroundPath.moveTo(lastX.toFloat(), lastY.toFloat())
                backgroundPath.lineTo(x.toFloat(), y.toFloat())
//                ctx.drawLine(lastX.toFloat(), lastY.toFloat(), x.toFloat(), y.toFloat(), hintPaint)
            } else {
                foregroundPath.moveTo(lastX.toFloat(), lastY.toFloat())
                foregroundPath.lineTo(x.toFloat(), y.toFloat())
//                ctx.drawLine(lastX.toFloat(), lastY.toFloat(), x.toFloat(), y.toFloat(), primaryPaint)
            }

            lastX = x
            lastY = y
        }
    }

    companion object {
        val sphere = SphereWireframe3D(50.0)
    }
}