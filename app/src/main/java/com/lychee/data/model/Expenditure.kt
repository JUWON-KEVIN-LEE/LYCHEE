package com.lychee.data.model

/**
 *  TODO realm
 *  지출내역
 */
data class Expenditure (
        var id : String? = null,
        var cardId : String? = null,
        var date : String? = null,
        var time : String? = null,
        var shopName : String? = null,
        var price : String? = null,
        var isForeign : Boolean? = null,
        var isApproved : Boolean? = null,
        var isInstallment : Boolean? = null,
        var monthsOfInstallment : Int? = null
) /* : RealmObject() */