package com.withapp.coffeememo.copyright.presentation.view

import androidx.lifecycle.ViewModel
import com.withapp.coffeememo.R
import com.withapp.coffeememo.copyright.presentation.model.CopyRightInfo

class CopyRightViewModel : ViewModel() {
    val copyRightInfoList: List<CopyRightInfo> =
        listOf(
            CopyRightInfo(
                "Freepik - Flaticon",
                "https://www.flaticon.com/free-icons/steam",
                R.drawable.aroma
            ),
            CopyRightInfo(
                "srip - Flaticon",
                "https://www.flaticon.com/free-icons/coffee",
                R.drawable.coffee_bag_icon
            )
        )
}