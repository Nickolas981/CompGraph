package ua.kpi.dongumen.compgraph.extensions

import android.widget.SeekBar

fun SeekBar.setOnProgressChanged(func: (seekBar: SeekBar?, progress: Int, fromUser: Boolean) -> Unit) {
    setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            func.invoke(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    })
}