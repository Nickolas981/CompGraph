package ua.kpi.dongumen.compgraph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class MyView : View {
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
    private val path = Path()
    var internalRadius = 400F
    var radius = 100F
    private val rectF = RectF(0f, radius, 0f, (radius + internalRadius *2))


    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        val halfWidth = c.width.toFloat() / 2
        val halfHeight = c.height.toFloat() / 2

        rectF.left = (halfWidth - internalRadius)
        rectF.right = (halfWidth + internalRadius)
        rectF.top = (halfHeight - internalRadius * 2 - radius)
        rectF.bottom = halfHeight - radius
        initV7(internalRadius, halfWidth, halfHeight)
        c.drawPath(path, paint)
        for (i in 0 until count - 1) {
            c.save()
            paint.color = Color.rgb(colorStep * i, 0, 0)
            c.rotate(degrees, (c.width / 2).toFloat(), (c.height / 2).toFloat())
            c.drawPath(path, paint)
        }
    }

    private fun initV4(halfRadius: Float, halfWidth: Float) {
        path.reset()
        path.addRect(rectF, Path.Direction.CW)
        path.moveTo((halfWidth - halfRadius), radius)
        path.lineTo((halfWidth + halfRadius), (radius + internalRadius))
        path.moveTo((halfWidth + halfRadius), radius)
        path.lineTo((halfWidth - halfRadius), (radius + internalRadius))
    }

    private fun initV7(halfRadius: Float, halfWidth: Float, halfHeight: Float) {
        path.reset()
        path.addRect(rectF, Path.Direction.CW)
        path.addCircle(halfWidth, halfHeight - halfRadius - radius, halfRadius, Path.Direction.CW)
    }
}
