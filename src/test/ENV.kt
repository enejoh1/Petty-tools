package org.example.horizonpay

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

object ENV {
    const val CHANNEL_ID = "57acc41abfcb4161a285e53d9d745a59"
    const val SECRET_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDy9Y6PkzPAqvvEiIZFxywiK9M5vd7RXe6YleQdBwL2rq930HbMdrD+3vn6LpW2cFlnTD90SUTo55m6akc+TLCg278VT+cTEFMeFGiPmQ1aVZ7UcN4fzvQ4WkzxgCpwc+xLusqSGcE3f3iOHZNcSddB7KHHdZkue/RJBXWuakpTWwIDAQAB"
    const val URL = "https://atomm-staging.9rapoint.com/"

    fun getSign(time: Long, channelId: String, secret: String): String {
        val signStr = "channelId=$channelId,secret=$secret,time=$time,version=1.0.0"
        return Hashing.sha256().hashString(signStr, StandardCharsets.UTF_8).toString()
    }
}