package uz.gita.game_2048_lazizjon.utils

import uz.gita.game_2048_lazizjon.R

object BackgroundUtils {
    val colors = arrayOf(
        R.drawable.bg_item_0,
        R.drawable.bg_item_2,
        R.drawable.bg_item_4,
        R.drawable.bg_item_8,
        R.drawable.bg_item_16,
        R.drawable.bg_item_32,
        R.drawable.bg_item_64,
        R.drawable.bg_item_128,
        R.drawable.bg_item_256,
        R.drawable.bg_item_512,
        R.drawable.bg_item_1024,
        R.drawable.bg_item_2048,
    )

    fun Int.getBackgroundByAmount() : Int {
        var amount = this
        var degree = 0
        while (amount > 0) {
            amount /=2
            degree ++
        }
        return BackgroundUtils.colors[degree]
    }
}