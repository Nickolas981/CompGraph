package ua.kpi.dongumen.compgraph.lab3

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.ScaleGestureDetector
import kotlinx.android.synthetic.main.activity_lab_three.*
import ua.kpi.dongumen.compgraph.R


class LabThreeActivity : AppCompatActivity() {

    val handler = Handler()
    val step = Math.PI / 90
    private var mActivePointerId = INVALID_POINTER_ID
    private var mLastTouchX = 0f
    private var mLastTouchY = 0f
    private val rotation = Rotation({
        my_view3.rotation = my_view3.rotation + step
        my_view3.invalidate()
    }, handler)
    private var running = false

    private var mScaleGestureDetector: ScaleGestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_three)
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        setTitle(R.string.second_lab)

//        toggleAnimation()
//        blast()
    }

    private fun toggleAnimation() {
        if (running) {
            handler.removeCallbacksAndMessages(null)
        } else {
            handler.postDelayed(rotation, 20)
        }
        running = !running
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

                    my_view3.dotRotation.y += dx / 1000
                    my_view3.dotRotation.x += dy / 1000

                    my_view3.invalidate()

                    // Remember this touch position for the next move event
                    mLastTouchX = x
                    mLastTouchY = y
                } catch (ignored: Exception) {
                }
            }
        }

        return true
    }

    fun blast() {
        val valueAnimator = ValueAnimator.ofFloat(0.01f, 10f, 0.01f)
        valueAnimator.duration = 800
        valueAnimator.addUpdateListener {
            my_view3.scale = it.animatedValue as Float
            my_view3.invalidate()
        }
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.start()
    }


    class Rotation(val run: () -> Unit, val handler: Handler) : Runnable {
        override fun run() {
            run.invoke()
            handler.postDelayed(this, 20)
        }

    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            SphereWireframeView.sphere = SphereWireframe3D(SphereWireframeView.sphere.radius * scaleGestureDetector.scaleFactor)
            my_view3.invalidate()
            return true
        }
    }

}