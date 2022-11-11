package uz.gita.game_2048_lazizjon.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.gita.game_2048_lazizjon.MainActivity

fun myLog(message: String, tag: String = "TTT") {
    Log.d(tag, message)
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.openScreen(fm: Fragment) {
    (requireActivity() as MainActivity).openScreen(fm)
}