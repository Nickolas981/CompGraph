package ua.kpi.dongumen.compgraph.lab1

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_lab_one.*
import ua.kpi.dongumen.compgraph.R
import ua.kpi.dongumen.compgraph.extensions.onClick
import ua.kpi.dongumen.compgraph.extensions.onLongClick
import ua.kpi.dongumen.compgraph.extensions.setOnProgressChanged

class LabOneActivity : AppCompatActivity() {

    private val pulsingAnimation by lazy {
        val valueAnimator = ValueAnimator.ofFloat(100f, 150F, 100f)
        valueAnimator.duration = 3000
        valueAnimator.addUpdateListener {
            my_view.internalRadius = it.animatedValue as Float
            my_view.invalidate()
        }

        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator
    }

    private val countAnimation by lazy {
        val valueAnimator = ValueAnimator.ofInt(1, 60, 1)
        valueAnimator.duration = 3000
        valueAnimator.addUpdateListener {
            my_view.count = it.animatedValue as Int
            my_view.invalidate()
        }

        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_one)
        setTitle(R.string.first_lab)
        radius.setOnProgressChanged { _, progress, _ ->
            my_view.radius = progress.toFloat()
            my_view.invalidate()
        }
        count.setOnProgressChanged { _, progress, _ ->
            my_view.count = progress
            my_view.invalidate()
        }
        internal_radius.setOnProgressChanged { _, progress, _ ->
            my_view.internalRadius = progress.toFloat()
            my_view.invalidate()
        }
        my_view.onClick { fromZeroToSixty() }
        my_view.onLongClick { startPulsing() }

    }

    private fun startPulsing() {
        when {
            pulsingAnimation.isPaused -> pulsingAnimation.resume()
            pulsingAnimation.isRunning -> pulsingAnimation.pause()
            else -> pulsingAnimation.start()
        }
    }

    private fun fromZeroToSixty() {
        Log.d("countAnimation", "isRunning:${countAnimation.isRunning}\ncountAnimation.isStarted:${countAnimation.isStarted}\nisPaused:${countAnimation.isPaused}\n")
        when {
            countAnimation.isPaused -> countAnimation.resume()
            countAnimation.isRunning -> countAnimation.pause()
            else -> countAnimation.start()
        }
    }

}