package com.dansdev.sampleeventpipe

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dansdev.libeventpipe.EventPipe
import com.dansdev.sampleeventpipe.event.ClearTextEvent
import com.dansdev.sampleeventpipe.event.UpdateTextEvent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerEvents()
    }

    private fun registerEvents() {
        EventPipe.registerEvent(UpdateTextEvent::class.java) { event ->
            tvResult.text = event.text
        }
        EventPipe.registerEvent(ClearTextEvent::class.java) { event ->
            tvResult.text = ""
        }
    }

    fun onSendEvent(v: View) {
        val data = etData.text.toString()
        EventPipe.send(UpdateTextEvent(data))
    }

    fun onClearEvent(v: View) {
        EventPipe.send(ClearTextEvent())
    }

    fun onRegisterEvent(v: View) {
        registerEvents()
    }

    fun onUnregisterEvent(v: View) {
        EventPipe.unregisterAllEvents()
    }
}
