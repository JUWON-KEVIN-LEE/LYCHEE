package com.lychee.data.core.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * 카드
 */
open class Card (
        @PrimaryKey
        var id: Int = 0,
        /**
         * 은행
         */
        var bank: String? = null,
        /**
         * 카드명칭 ex. 현대카드X
         */
        var cardName: String? = null,
        /**
         * 카드번호 ex. 1*2*, 3*7*
         */
        var cardNumber: String? = null,
        /**
         * 현재 카드로 사용한 총 금액
         */
        var accumulation: Int = 0
) : RealmObject()