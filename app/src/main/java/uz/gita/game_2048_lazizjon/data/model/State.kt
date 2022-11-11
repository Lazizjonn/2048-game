package uz.gita.game_2048_lazizjon.data.model

data class State(
        val swipe: Boolean,
        val score : Int,
        val move: Int,
        val record: Int,
        val array: Array<IntArray>
)