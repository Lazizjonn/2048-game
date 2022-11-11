package uz.gita.game_2048_lazizjon.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import uz.gita.game_2048_lazizjon.data.enums.SideEnum
import kotlin.math.abs

class MyTouchListener(context: Context) : View.OnTouchListener {

    private var resultListener: ((SideEnum) -> Unit)? = null

    private val gesture = GestureDetector(context, MyGestureDetector())

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
         gesture.onTouchEvent(event)
        return true
    }

    inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {

        private val MIN_MOVE = 100

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (abs(e2.x - e1.x) > abs(e2.y - e1.y)) {
                if (abs(e2.x - e1.x) < MIN_MOVE) return false
                if (e2.x > e1.x) {
                    resultListener?.invoke(SideEnum.RIGHT)
                } else resultListener?.invoke(SideEnum.LEFT)
                // horizontal
            } else {
                // vertical
                if (abs(e2.y - e1.y) < MIN_MOVE) return false
                if (e2.y > e1.y) {
                    resultListener?.invoke(SideEnum.DOWN)
                } else resultListener?.invoke(SideEnum.UP)
            }
            return true
        }
    }

    fun setResultListener(block: (SideEnum) -> Unit) {
        this.resultListener = block
    }
}