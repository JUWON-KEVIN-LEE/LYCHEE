package com.lychee.data.core.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Place(
        @PrimaryKey
        var id: Int = 0,
        /**
         * 가게명
         */
        var name: String? = null,
        /**
         * 실제 주소
         */
        var address: String? = null,
        /**
         * LAT / LNG
         */
        var latitude: Double = 0.0,
        var longitude: Double = 0.0,
        /**
         * 한 장소에서 사용한 총 금액
         */
        var accumulation: Int = 0,
        /**
         * 방문횟수
         */
        var visitCount: Int = 0,
        /**
         * 방문일자들
         */
        var dates: RealmList<Date>? = null
): RealmObject()