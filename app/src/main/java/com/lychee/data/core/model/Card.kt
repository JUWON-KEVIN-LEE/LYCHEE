package com.lychee.data.core.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * 카드
 */
open class Card (
        @PrimaryKey
        var id: Int = 0,
        var bank: String = "",
        var cardName: String = "",
        var cardNumber: String = ""
) : RealmObject()