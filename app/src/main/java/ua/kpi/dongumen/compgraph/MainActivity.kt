package ua.kpi.dongumen.compgraph

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ua.kpi.dongumen.compgraph.extensions.onClick
import ua.kpi.dongumen.compgraph.extensions.startActivity
import ua.kpi.dongumen.compgraph.lab1.LabOneActivity
import ua.kpi.dongumen.compgraph.lab2.LabTwoActivity
import ua.kpi.dongumen.compgraph.lab3.LabThreeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        first_lab.onClick { startActivity<LabOneActivity>() }
        second_lab.onClick { startActivity<LabTwoActivity>() }
        third_lab.onClick { startActivity<LabThreeActivity>() }
    }
}
