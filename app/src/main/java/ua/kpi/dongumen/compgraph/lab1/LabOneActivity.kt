package ua.kpi.dongumen.compgraph.lab1

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lab_one.*
import ua.kpi.dongumen.compgraph.R
import ua.kpi.dongumen.compgraph.extensions.setOnProgressChanged

class LabOneActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_lab_one)
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
    }

}