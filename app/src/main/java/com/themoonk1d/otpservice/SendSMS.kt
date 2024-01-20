package com.themoonk1d.otpservice
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.snackbar.Snackbar

@RequiresApi(Build.VERSION_CODES.O)
class SendSMS(private val context : Activity, private val otp : String, private val phone : String) {
    private val view: View? = context.findViewById<View>(android.R.id.content)
    companion object {
        private const val REQUEST_SMS_PERMISSION = 101
    }
    private fun grantPer (){
        ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.SEND_SMS), REQUEST_SMS_PERMISSION)
    }

    init {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.SEND_SMS), REQUEST_SMS_PERMISSION)
            if (view != null) {
                Snackbar.make(view, "Need Permission to send SMS", Snackbar.LENGTH_LONG).setAction("Grant Now", View.OnClickListener {
                    ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.SEND_SMS), REQUEST_SMS_PERMISSION)
                }).show()

            }
        } else {
            if (view != null) {
                Snackbar.make(view, "Sending ...", Snackbar.LENGTH_LONG).show()
            }
        }
        sendSMS()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendSMS() {
        val sentPI: PendingIntent = PendingIntent.getBroadcast(context, 0, Intent("SMS_SENT"),
            PendingIntent.FLAG_IMMUTABLE)
        //context.getSystemService(SmsManager::class.java)
        getSystemService(context, SmsManager::class.java)?.sendTextMessage(phone, null, otp, sentPI, null)
        context.registerReceiver(sentReceiver, IntentFilter("SMS_SENT"),
            Context.RECEIVER_NOT_EXPORTED)
        sentReceiver
    }

    private val sentReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Toast.makeText(context, "✅", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "❌", Toast.LENGTH_SHORT).show()
                }
            }
            context.unregisterReceiver(this)
        }
    }

}

