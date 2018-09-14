package ua.kpi.dongumen.compgraph.lab2

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lab_two.*
import ua.kpi.dongumen.compgraph.R
import ua.kpi.dongumen.compgraph.extensions.onClick
import ua.kpi.dongumen.compgraph.extensions.onLongClick

class LabTwoActivity : AppCompatActivity() {

    private val countAnimation by lazy {
        val valueAnimator = ValueAnimator.ofInt(10, 300, 10)
        valueAnimator.duration = 3000
        valueAnimator.addUpdateListener {
            my_view2.count = it.animatedValue as Int
            my_view2.invalidate()
        }

        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator
    }

    private val pulsingAnimation by lazy {
        val valueAnimator = ValueAnimator.ofInt(20, 250, 20)
        valueAnimator.duration = 3000
        valueAnimator.addUpdateListener {
            my_view2.r = it.animatedValue as Int
            my_view2.invalidate()
        }

        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_two)
        setTitle(R.string.second_lab)

        my_view2.onClick { fromZeroToSixty() }
        my_view2.onLongClick { startPulsing() }

    }


    private fun startPulsing() {
        when {
            pulsingAnimation.isPaused -> pulsingAnimation.resume()
            pulsingAnimation.isRunning -> pulsingAnimation.pause()
            else -> pulsingAnimation.start()
        }
    }

    private fun fromZeroToSixty() {
        when {
            countAnimation.isPaused -> countAnimation.resume()
            countAnimation.isRunning -> countAnimation.pause()
            else -> countAnimation.start()
        }
    }
}