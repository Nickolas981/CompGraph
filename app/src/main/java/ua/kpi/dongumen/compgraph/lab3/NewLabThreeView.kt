package ua.kpi.dongumen.compgraph.lab3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ua.kpi.dongumen.compgraph.extensions.fori


class NewLabThreeView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var rotation = 0.0
    var distance = 800.0
    var scale = 1f

    private var dotSize = 5
    var offsetX = 0f
    //        get() = if (offsetX < scale * 5) offsetX else scale * 5
    var offsetY = 0f
//        get() = if (offsetY < scale * 5) offsetY else scale * 5

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

    class Point3D {
        var x = 0.0
        var y = 0.0
        var z = 0.0
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        render(canvas!!)
    }

    class Sphere3D(val radius: Double) {
        var point = mutableListOf<Point3D>()

        init {
            fori(0.0, { it <= 6.28 }, { it + 0.17 })
            { alpha ->
                point.add(Point3D())
                val p = point.last()

                p.x = Math.cos(alpha) * this.radius
                p.y = 0.0
                p.z = Math.sin(alpha) * this.radius
            }

            // Ciclo da 0ø a 90ø con passo di 10ø...calcola la prima semisfera (direction = 1)
            // Ciclo da 0ø a 90ø con passo di 10ø...calcola la seconda semisfera (direction = -1)

            fori(1, { it >= -1 }, { it - 2 }) { direction ->
                fori(0.17, { it < 1.445 }, { it + 0.17 }) { beta ->

                    val radius = Math.cos(beta) * this.radius
                    val fixedY = Math.sin(beta) * this.radius * direction

                    fori(0.0, { it < 6.28 }, { it + 0.17 }) { alpha ->
                        point.add(Point3D())
                        val p = point.last()

                        p.x = Math.cos(alpha) * radius
                        p.y = fixedY
                        p.z = Math.sin(alpha) * radius
                    }
                }
            }
        }


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

    fun render(ctx: Canvas) {
        val width = ctx.width
        val height = ctx.height
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
                        drawPoint(ctx, x + offsetX, y + offsetY)
                        //drawPointWithGradient(ctx, x, y, 10, "rgb(0,200,0)", 0.8);
                    }
                }
            }
        }
    }

    fun drawPoint(ctx: Canvas, x: Double, y: Double) {
        ctx.drawCircle(x.toFloat(), y.toFloat(), dotSize.toFloat() * scale, primaryPaint)
    }

    fun drawBackPoint(ctx: Canvas, x: Double, y: Double) {
        ctx.drawCircle(x.toFloat(), y.toFloat(), (dotSize * 0.7).toFloat() * scale / 2, hintPaint)
    }

    companion object {
        val sphere = Sphere3D(50.0)
    }
}