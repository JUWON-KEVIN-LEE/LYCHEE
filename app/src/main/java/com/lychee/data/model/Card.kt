package com.lychee.data.model

import io.realm.RealmObject

/**
 * TODO DATA STRUCTURE 고민
 * 카드
 */
open class Card (
        var id : Long = 0,
        var url : String = "",
        var bank : String = "",
        var cardName : String = "",
        var cardNumber : String = ""
) : RealmObject()