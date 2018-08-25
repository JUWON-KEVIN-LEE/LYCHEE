package com.lychee.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.lychee.data.model.Expenditure

class SmsRecevier: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent
                ?.takeIf { SMS_RECEIVED_ACTION == it.action }
                ?.let {
                    // above Build.VERSION_CODES.KITKAT
                    val messages = Telephony.Sms.Intents.getMessagesFromIntent(it)
                    val message = messages[0]
                    val sender = message.displayOriginatingAddress
                    val body = message.messageBody

                    if(checkTargetSender(sender)) {
                        val expenditure = parseValidBody(body)

                    }
                }
    }

    fun checkTargetSender(sender: String): Boolean {
        // sender info 를 기반으로 target sender 인지 확인 TODO

        return true
    }

    fun parseValidBody(body: String): Expenditure {
        // parcer 를 이용해서 SMS body 를 Exp 객체로 변환 TODO
        return Expenditure()
    }

    companion object {
        private const val SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED"
    }
}