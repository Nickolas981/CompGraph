package ua.kpi.dongumen.compgraph.lab3

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class LabThreeView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val radius = 300


    private val paint by lazy {
        Paint().apply { style = Paint.Style.STROKE }
                .apply { color = Color.rgb(0, 0, 0) }
                .apply { strokeWidth = 2f }
    }
    private val path = Path()

}