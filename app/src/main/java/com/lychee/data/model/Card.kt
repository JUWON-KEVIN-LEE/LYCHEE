package com.lychee.data.model

data class Card (
        var id : Long = 0,
        var url : String = "",
        var bank : String = "",
        var cardName : String = "",
        var cardNumber : String = ""
)

/*
* http://www.card-gorilla.com/upload/2013check_b3.png 신한
* http://www.card-gorilla.com/upload/kb_chsinglecheck.png KB
* http://www.card-gorilla.com/upload/nhcH_20habom.png 농협
* http://www.card-gorilla.com/upload/WR_SUM.PNG 우리
* http://www.card-gorilla.com/upload/hn_kakaopaycheck.png 카카오
* */