package com.lychee.sms.model

import com.lychee.sms.parser.*

/**
 * 카드사
 */
object Target {

    private const val _KB = "15881688"
    private const val _LOTTE = "15888100"
    private const val _HYUNDAI = "15776000"
    private const val _SHINHAN = "15447200"
    private const val _HANA = "18001111"
    private const val _IBK = "15662566"
    private const val _NH = "15881600"
    private const val _SAMSUNG = "15888900"
    private const val _ME = "01042493646" // TEST

    /**
     * Selection Args
     */
    val ADDRESSES = arrayOf(
            _KB,
            _LOTTE,
            _HYUNDAI,
            _SHINHAN,
            _HANA,
            _IBK,
            _NH,
            _ME /* TEST */
    )

    fun checkTargetAddressAndGetParser(address: String): Parser? {
        return when (address) {
            _KB -> KB
            _LOTTE -> Lotte
            _HYUNDAI -> Hyundai
            _SHINHAN -> Shinhan
            _HANA -> Hana
            _IBK -> IBK
            _NH -> NH
            _SAMSUNG -> Samsung
            /* TEST */
            _ME -> Shinhan
            else -> null
        }
    }
}
