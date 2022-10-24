package com.example.cryptomarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinAboutItem (


    var coinWeb:String?="no-data",
    var coinGithub:String?="no-data",
    var coinTwitter:String?="no-data",
    var coinDecription:String?="no-data",
    var coinReddit:String?="no-data"








        ):Parcelable