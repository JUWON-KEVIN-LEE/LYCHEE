package com.lychee.util

import com.lychee.data.model.Expenditure

/**
 * TODO Convert to kotlin
 * 메시지를 분해하고 지출 내역 객체로 파싱
 */
class SmsParcer {

    lateinit var words : MutableList<String>

    fun parse(body : String, company : String) : Expenditure? {
        val expenditure = Expenditure()

        words = split(body)

        return null
    }

    /**
     *  의미 단위로 쪼개기
     */
    private fun split(body : String) : MutableList<String> {
        val words : ArrayList<String> = ArrayList()

        // 개행 단위
        for(word_ in body.split("\n")) {
            word_.run { // "(" or ")" or 공백 제거
                    replace("(", " ")
                    replace(")", " ")
                    split(" ")
                }
                // words 에 저장
                .let { for(word in it) words.add(word) }
        }

        return words
    }

    private fun parseCardId(company : String, words : MutableList<String>, expenditure: Expenditure) {
        var cardId = ""

        for(i in words.indices) {
            // 회사 이름 또는 카드 라는 단어를
            // 가지고 있으면
            words[i].takeIf { it.contains("카드").or(it.contains(company)) }
                    ?.let { cardId += it
                        // card id 가 다음 조각에 있을 수 있다.
                        if(!containsCardId(it)) {
                            cardId += words[i+1]
                            words.removeAt(i+1)
                        }
                        words.removeAt(i)


                        expenditure.cardId = cardId
                    }
        }
    }

    private fun containsCardId(word : String) : Boolean
            = word.contains("*").or(word.length >= 6 && "KB국민카드" != word)

    private fun containsAppr(word : String, expenditure: Expenditure) : Boolean {
        if(word.contains("해외")) {
            expenditure.isForeign = true
            word.replace("해외", "")
        }

        if (word.contains("승인")
                && !word.contains("취소")
                && !word.contains("거절")) {

            expenditure.isApproved = true
            word.replace("승인", "")
        }

        if (word.contains("취소")) {
            expenditure.isApproved = false

            if (word.contains("승인"))
                word.replace("승인취소", "")
            else
                word.replace("취소", "")
        }

        return false
    }
}

