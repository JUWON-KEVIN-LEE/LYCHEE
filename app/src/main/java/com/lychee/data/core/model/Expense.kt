package com.lychee.data.core.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 *  지출내역
 */
open class Expense (
        @PrimaryKey
        var id:Int = 0,
        var userName:String? = null,
        var card:Card? = null,
        var dateTime:Date? = null,
        var category:Category? = null,
        var shopName:String? = null,
        var charge:Int = 0,
        var approved:Boolean= true,
        /* 할부 */
        var installment:Int = 0,
        var isForeign:Boolean = false,
        var place:Place? = null
) : RealmObject() {

    override fun toString(): String {
        return "ID: $id, " +
                "사용자: $userName, " +
                "카드: ${card?.bank} ${card?.cardName ?: card?.cardNumber}, " +
                "날짜: $dateTime, " +
                "카테고리: ${category?.category}, " +
                "상점명: $shopName, " +
                "청구된 금액: $charge, " +
                "허용 여부: $approved, " +
                "할부 기간: ${if(installment == 0) "일시불" else "${installment}개월"}"
    }
}