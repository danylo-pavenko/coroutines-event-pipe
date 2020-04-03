package com.dansdev.sampleeventpipe

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dansdev.libeventpipe.EventPipe
import com.dansdev.sampleeventpipe.event.UpdateTextEvent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventPipe.registerEvent(this.javaClass.simpleName, Dispatchers.Main, UpdateTextEvent::class.java) { event ->
            tvResult.text = event.text
        }
    }

    fun onSendEvent(v: View) {
        val data = etData.text.toString()
        EventPipe.send(UpdateTextEvent(data))
    }
}
