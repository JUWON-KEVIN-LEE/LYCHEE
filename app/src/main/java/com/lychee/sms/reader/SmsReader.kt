package com.lychee.sms.reader

import android.content.Context
import android.provider.Telephony
import com.lychee.sms.model.Sms
import com.lychee.sms.model.Target.ADDRESSES

fun readSms(
        context: Context,
        targetAddresses: Array<String> = ADDRESSES
): List<Sms> {

    val result = mutableListOf<Sms>()

    val contentResolver = context.contentResolver

    val cursor = contentResolver.query(Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(Telephony.Sms.Inbox._ID,
                    Telephony.Sms.Inbox.THREAD_ID,
                    Telephony.Sms.Inbox.ADDRESS,
                    Telephony.Sms.Inbox.DATE,
                    Telephony.Sms.Inbox.BODY),
            "${Telephony.Sms.Inbox.ADDRESS} = ?",
            targetAddresses,
            "date DESC")

    if(cursor.moveToFirst()) {
        with(cursor) {
            for(i in 0 until count) {
                val sms = Sms(
                        getString(getColumnIndexOrThrow(Telephony.Sms.Inbox._ID)),
                        getString(getColumnIndexOrThrow(Telephony.Sms.Inbox.THREAD_ID)),
                        getString(getColumnIndexOrThrow(Telephony.Sms.Inbox.ADDRESS)),
                        getString(getColumnIndexOrThrow(Telephony.Sms.Inbox.DATE)),
                        getString(getColumnIndexOrThrow(Telephony.Sms.Inbox.BODY))
                )

                result.add(sms)

                cursor.moveToNext()
            }
        }
    }

    cursor.close()

    return result
}