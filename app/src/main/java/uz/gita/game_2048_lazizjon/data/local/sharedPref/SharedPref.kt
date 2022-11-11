package uz.gita.game_2048_lazizjon.data.local.sharedPref

import android.content.Context
import android.content.SharedPreferences
import uz.gita.game_2048_lazizjon.data.model.State

class SharedPref {

    companion object {
        private lateinit var instance: SharedPref
        private lateinit var sharedPref: SharedPreferences

        fun init(context: Context) {
            if (::instance.isInitialized) return
            instance = SharedPref()
            sharedPref = context.getSharedPreferences("GAME", Context.MODE_PRIVATE)
        }

        fun get() = instance
    }

    fun saveRecord(record: Int) {
        sharedPref.edit().putInt("RECORD", record).apply()
    }

    fun isFirst(): Boolean {
        val isFirst = sharedPref.getBoolean("ISFIRST", true)
        sharedPref.edit().putBoolean("ISFIRST", false).apply()
        return isFirst
    }

    fun saveState(state: State) = with(state) {
        val edit = sharedPref.edit()
        edit.putInt("SCORE", score)
        edit.putBoolean("SWIPE", swipe)
        edit.putInt("MOVE", move)
        saveRecord(record)

        for (i in array.indices) {
            for (j in array.indices) {
                edit.putInt("Array${i}vs$j", array[i][j])
            }
        }
        edit.apply()
    }

    fun getState(): State = with(sharedPref) {
        val matrix = Array(4) { IntArray(4) }

        for (i in 0..3) {
            for (j in 0..3) {
                matrix[i][j] = getInt("Array${i}vs$j", 0)
            }
        }

        return State(
            getBoolean("SWIPE", false),
            getInt("SCORE", 0),
            getInt("MOVE", 0),
            getInt("RECORD", 0),
            matrix
        )
    }
}