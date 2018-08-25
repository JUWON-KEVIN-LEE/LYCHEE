package com.lychee.mock

import com.lychee.data.model.Card
import com.lychee.data.model.Expenditure

/**
 *  TEST
 */
object MockData {
    fun get() : List<Expenditure>
        = mutableListOf(
            Expenditure(0,
                    "",
                    "현대카드",
                    "2018.06.03",
                    "20:42",
                    "GS25 서울역점",
                    1514800,
                    false,
                    true,
                    false,
                    0,
                    37.56,
                    126.97),
            Expenditure(1,
                    "",
                    "삼성카드",
                    "2018.06.03",
                    "18:55",
                    "파리바게트",
                    16000,
                    false,
                    true,
                    false,
                    0,
                    37.33,
                    126.58),
            Expenditure(2,
                    "",
                    "국민카드",
                    "2018.06.03",
                    "10:23",
                    "서울병원",
                    132000,
                    false,
                    true,
                    false,
                    0,
                    37.12,
                    126.92),
            Expenditure(3,
                    "",
                    "현대카드",
                    "2018.06.02",
                    "19:11",
                    "카페 Alef",
                    2800,
                    false,
                    true,
                    false,
                    0,
                    37.56,
                    126.97),
            Expenditure(4,
                    "",
                    "신한카드",
                    "2018.06.01",
                    "18:05",
                    "스타벅스 서울대점",
                    11201800,
                    false,
                    true,
                    false,
                    0,
                    37.56,
                    126.97)
    )

    fun get_() : List<Card>
        = mutableListOf(
            Card(0, "신한카드", "", cardNumber = "8418"),
            Card(1, "KB국민카드", "", cardNumber = "1234"),
            Card(2, "하나카드", "", cardNumber = "1597"),
            Card(3, "IBK기업은행", "", cardNumber = "3696"),
            Card(4, "케이뱅크", "", cardNumber = "2580")
        )
}