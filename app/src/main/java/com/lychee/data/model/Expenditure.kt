package com.lychee.data.model

import io.realm.RealmObject

/**
 *  지출내역
 */
open class Expenditure(
        // @PrimaryKey
        var id: Long = 0,
        var cardId: String = "",
        var date: String = "",
        var time: String = "",
        var shopName: String = "",
        var price: String = "",
        var isForeign: Boolean = false,
        var isApproved: Boolean= true,
        var isInstallment: Boolean = false,
        var monthsOfInstallment: Int = 0
) : RealmObject()