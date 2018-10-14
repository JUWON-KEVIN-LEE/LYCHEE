package com.lychee.sms.model

data class Sms (
        val id: String,
        val threadId: String,
        val address: String,
        val date: String,
        val body: String
)