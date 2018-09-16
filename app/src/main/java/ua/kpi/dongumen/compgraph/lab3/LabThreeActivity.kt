package ua.kpi.dongumen.compgraph.lab3

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.ScaleGestureDetector
import kotlinx.android.synthetic.main.activity_lab_three.*
import ua.kpi.dongumen.compgraph.R


class LabThreeActivity : AppCompatActivity() {


    private var mActivePointerId = INVALID_POINTER_ID
    private var mLastTouchX = 0f
    private var mLastTouchY = 0f


    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private val scrollInterpolation by lazy {
        ScrollInterpolation({ x, y ->
            my_view3.dotRotation.y += x / 1000
            my_view3.dotRotation.x += y / 1000
            my_view3.invalidate()
        }, Handler())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_three)
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        setTitle(R.string.second_lab)

    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        mScaleGestureDetector?.onTouchEvent(motionEvent)

        val action = motionEvent.actionMasked

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                val pointerIndex = motionEvent.actionIndex
                val x = motionEvent.getX(pointerIndex)
                val y = motionEvent.getY(pointerIndex)

                // Remember where we started (for dragging)
                mLastTouchX = x
                mLastTouchY = y
                scrollInterpolation.refresh(0.0, 0.0)
                // Save the ID of this pointer (for dragging)
                mActivePointerId = motionEvent.getPointerId(0)
            }

            MotionEvent.ACTION_MOVE -> {
                try {// Find the index of the active pointer and fetch its position
                    val pointerIndex = motionEvent.findPointerIndex(mActivePointerId)

                    val x = motionEvent.getX(pointerIndex)
                    val y = motionEvent.getY(pointerIndex)


                    val dx = x - mLastTouchX
                    val dy = y - mLastTouchY

                    Log.d("DY", dy.toString())
                    Log.d("DX", dx.toString())

                    scrollInterpolation.refresh(dx.toDouble(), dy.toDouble())

                    // Remember this touch position for the next move event
                    mLastTouchX = x
                    mLastTouchY = y
                } catch (ignored: Exception) {
                }
            }
        }

        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            SphereWireframeView.sphere = SphereWireframe3D(SphereWireframeView.sphere.radius * scaleGestureDetector.scaleFactor)
            my_view3.invalidate()
            return true
        }
    }

    class ScrollInterpolation(private val run: (Double, Double) -> Unit, private val handler: Handler) {

        @Volatile
        private var scrollX = 0.0
        @Volatile
        private var scrollY = 0.0

        fun refresh(dx: Double = scrollX, dy: Double = scrollY) {
            scrollX = dx
            scrollY = dy
            handler.removeCallbacksAndMessages(null)
            handler.post {
                run.invoke(scrollX, scrollY)
                when {
                    scrollX < -1 -> when {
                        scrollY < -1 -> refresh(scrollX + 0.1, scrollY + 0.1)
                        scrollY > 1 -> refresh(scrollX + 0.1, scrollY - 0.1)
                        else -> refresh(scrollX + 0.1)
                    }
                    scrollX > 1 -> when {
                        scrollY < -1 -> refresh(scrollX - 0.1, scrollY + 0.1)
                        scrollY > 1 -> refresh(scrollX - 0.1, scrollY - 0.1)
                        else -> refresh(scrollX - 0.1)
                    }
                }
                when {
                    scrollY < -1 -> when {
                        scrollX < -1 -> refresh(scrollX + 0.1, scrollY + 0.1)
                        scrollX > 1 -> refresh(scrollX - 0.1, scrollY + 0.1)
                        else -> refresh(scrollX + 0.1)
                    }
                    scrollY > 1 -> when {
                        scrollX < -1 -> refresh(scrollX + 0.1, scrollY - 0.1)
                        scrollX > 1 -> refresh(scrollX - 0.1, scrollY - 0.1)
                        else -> refresh(scrollX - 0.1)
                    }
                }
            }
        }

    }

}