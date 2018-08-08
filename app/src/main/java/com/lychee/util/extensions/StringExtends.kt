package com.lychee.util.extensions

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

                    var result = it
                    for(i in 0 until comma) {
                        val point = 4 * i + start
                        result = result.replaceRange(0, point, result.substring(0, point) + ",")
                    }
                    return result
                }
                ?: this

/**
 * [ param = true ]
 * 10000 => 10,000원
 * 1254200 => 1,254,200원
 * [ param = false ]
 * 10000 => 10000원
 * 1254200 => 1254200원
 */
fun String.won(comma : Boolean) : String
        = takeIf { comma }
        ?.let { comma().plus("원") }
        ?:let { plus("원") }