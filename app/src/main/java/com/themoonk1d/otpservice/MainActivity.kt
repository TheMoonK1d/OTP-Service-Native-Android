package com.themoonk1d.otpservice

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val settingBtn = findViewById<ImageButton>(R.id.setting_btn)
        val win = window
        val client = OkHttpClient()
        win.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
        val res : Request = Request.Builder().url("ws://192.168.1.3:5000/otp-ws").build()
        val listener = WebSocketListener(context = this)
        val ws : WebSocket = client.newWebSocket(res, listener)
        client.dispatcher.executorService.shutdown()

        settingBtn.setOnClickListener(){
            Toast.makeText(this, "Soon", Toast.LENGTH_SHORT).show()
        }
    }
}