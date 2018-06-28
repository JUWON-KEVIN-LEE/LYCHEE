package com.lychee.data.model

import io.realm.RealmObject

/**
 *  지출내역
 *  카테고리
 */
open class Expenditure (
        // @PrimaryKey
        var id: Long = 0,
        var cardNumber : String = "", // 4*3*
        var cardName: String = "", // 또는 현대카드 X ( 카드 이름 )
        var date: String = "",
        var time: String = "",
        var shopName: String = "",
        var price: String = "",
        var isForeign: Boolean = false,
        var isApproved: Boolean= true,
        var isInstallment: Boolean = false,
        var monthsOfInstallment: Int = 0,
        // MAP
        var lat : Double = .0,
        var lng : Double = .0
) : RealmObject()