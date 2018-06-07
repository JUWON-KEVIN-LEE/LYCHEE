package com.lychee.extensions

/**
 *  Custom Extensions
 */

fun String.comma() : String
        = takeIf { isNotEmpty() }
                ?.let {
                    var comma = length / 3   // comma 개수
                    val start : Int               // 시작점
                    if(length % 3 == 0) { comma -= 1; start = 3 }
                    else start = length % 3

                    var res = it
                    for(i in 0 until comma) {
                        val point = 4 * i + start
                        res = res.replaceRange(0, point, res.substring(0, point) + ",")
                    }
                    return res }
                ?: this

fun String.won(comma : Boolean) : String
        = takeIf { comma }
        ?.let { comma().plus("원") }
        ?:let { plus("원") }