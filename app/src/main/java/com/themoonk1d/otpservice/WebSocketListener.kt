package com.themoonk1d.otpservice

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import kotlinx.serialization.json.Json


class WebSocketListener(val context: Activity) : WebSocketListener() {

    private var counter : Int = 0
    private val data : ArrayList<DataModel> = ArrayList()
    private val rootView: View? = context.findViewById<View>(android.R.id.content)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        msg(response.message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        msg("onMessage: $text")
        context.runOnUiThread(Runnable {
            val lst = context.findViewById<RecyclerView>(R.id.r_view)
            val w = DataModel(
                "Welcome",
                "Connected"
            )
            if (counter == 0){
                counter ++
                data.add(w)
            }else{

                SendSMS(
                    context,
                    Json.decodeFromString<DataModel>(text).otp,
                    Json.decodeFromString<DataModel>(text).to
                )
                data.add(Json.decodeFromString<DataModel>(text))
            }
            lst.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            lst.adapter = DataAdapter(data)
        })
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        webSocket.close(1000, null)
        msg("Closing: $code/$reason")
        if (rootView != null) {
            Snackbar.make(rootView, "Closing Connection", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        if (rootView != null) {
            Snackbar.make(rootView, "Closed",Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        msg("Err ${t.message} $response ")
        if (rootView != null) {
            Snackbar.make(rootView, "Connecting", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun msg (t : String){
        Log.e("msg: ",t)
    }
}



