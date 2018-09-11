package ua.kpi.dongumen.compgraph

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ua.kpi.dongumen.compgraph.extensions.setOnProgressChanged

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
