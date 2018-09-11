package ua.kpi.dongumen.compgraph.extensions

import android.view.View

fun View.onClick(func: (View) -> Unit) {
    setOnClickListener { func.invoke(it) }
}


fun View.onLongClick(func: () -> Unit) {
    setOnLongClickListener {
        func.invoke()
        true
    }
}