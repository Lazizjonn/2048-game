package uz.gita.game_2048_lazizjon.mvp.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import uz.gita.game_2048_lazizjon.R
import uz.gita.game_2048_lazizjon.data.enums.SideEnum
import uz.gita.game_2048_lazizjon.data.model.State
import uz.gita.game_2048_lazizjon.mvp.contracts.PlayContract
import uz.gita.game_2048_lazizjon.mvp.presenters.PlayPresenter
import uz.gita.game_2048_lazizjon.utils.BackgroundUtils.getBackgroundByAmount
import uz.gita.game_2048_lazizjon.utils.MyTouchListener
import uz.gita.game_2048_lazizjon.utils.openScreen


class PlayView : Fragment(R.layout.screen_play), PlayContract.View {
    private val presenter: PlayContract.Presenter = PlayPresenter(this)
    private val buttons: MutableList<TextView> = ArrayList(16)
    private lateinit var score: TextView
    private lateinit var best: TextView
    private lateinit var move: TextView
    private lateinit var myView: View
    private var swipe = false
    private var scoreVal = 0
    private var bestVal = 0
    private var moveVal = 0
    private var array: Array<IntArray> = arrayOf(
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0)
    )
    private lateinit var mainView: LinearLayoutCompat
    private lateinit var myTouchListener: MyTouchListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myView = view
        loadViews(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun loadViews(view: View) {
        score = view.findViewById(R.id.button_score)
        move = view.findViewById(R.id.button_move)
        best = view.findViewById(R.id.button_best)

        view.findViewById<AppCompatButton>(R.id.button_2048).setOnClickListener {

        }
        view.findViewById<AppCompatButton>(R.id.button_new).setOnClickListener {
            presenter.restart()
        }

        mainView = view.findViewById(R.id.mainView)

        for (i in 0 until mainView.childCount) {
            val line: LinearLayoutCompat = mainView.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until line.childCount) {
                buttons.add(line.getChildAt(j) as TextView)
            }
        }
        myTouchListener = MyTouchListener(requireContext())
        myTouchListener.setResultListener {
            when (it) {
                SideEnum.DOWN -> presenter.swipeSideDown()
                SideEnum.UP -> presenter.swipeSideUp()
                SideEnum.LEFT -> presenter.swipeSideLeft()
                SideEnum.RIGHT -> presenter.swipeSideRight()
            }
        }
        mainView.setOnTouchListener(myTouchListener)

        presenter.loadData()
    }

    @SuppressLint("SetTextI18n")
    override fun addMove(extra: Int) {
        moveVal++
        scoreVal += extra
        move.text = "$moveVal move" + if (moveVal != 1) "s" else ""
        score.text = SpannableStringBuilder().append("score\n").bold { append("$scoreVal") }
        if (scoreVal > bestVal) {
            bestVal = scoreVal
            best.text = SpannableStringBuilder().append("best\n").bold { append("$bestVal") }
            presenter.saveRecord(scoreVal)
        }
    }

    override fun illustrateMatrix(matrix: Array<IntArray>) {
        array = matrix
        var change = false
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                if (buttons[4 * i + j].text.toString().isNotEmpty())
                    change = matrix[i][j] != buttons[i * 4 + j].text.toString().toInt()
                else
                    change = true
                if (matrix[i][j] == 0)
                    buttons[4 * i + j].text = ""
                else {
                    buttons[4 * i + j].text = matrix[i][j].toString()
                }
                buttons[4 * i + j].setBackgroundResource(matrix[i][j].getBackgroundByAmount())
                if (buttons[4 * i + j].text.toString().isEmpty())
                    continue
                val temp = buttons[4 * i + j].text.toString().toInt()
                if (temp < 8) {
                    buttons[4 * i + j].setTextColor(Color.parseColor("#776e65"))
                } else {
                    buttons[4 * i + j].setTextColor(Color.parseColor("#f9f6f2"))
                }
            }
        }
    }

    override fun finishScreen() {
        openScreen(FinishView())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setSwipeEverywhere(ok: Boolean) {
        if (ok) {
            myView.findViewById<ConstraintLayout>(R.id.root).isEnabled = true
            val myTouchListener = MyTouchListener(requireContext())
            myTouchListener.setResultListener {
                when (it) {
                    SideEnum.DOWN -> presenter.swipeSideDown()
                    SideEnum.UP -> presenter.swipeSideUp()
                    SideEnum.LEFT -> presenter.swipeSideLeft()
                    SideEnum.RIGHT -> presenter.swipeSideRight()
                }
            }
            myView.findViewById<ConstraintLayout>(R.id.root).setOnTouchListener(myTouchListener)
            return
        }
        //myView.findViewById<ConstraintLayout>(R.id.root).isEnabled = false
    }

    @SuppressLint("SetTextI18n")
    override fun loadData(state: State) {
        with(state) {
            setSwipeEverywhere(swipe)
            scoreVal = score
            bestVal = record
            moveVal = move
            illustrateMatrix(array)
        }
        score.text = SpannableStringBuilder().append("score\n").bold { append("$scoreVal") }
        best.text = SpannableStringBuilder().append("best\n").bold { append("$bestVal") }
        move.text = "$moveVal move" + if (moveVal != 1) "s" else ""
    }


    override fun onResume() {
        presenter.loadData()
        super.onResume()
    }

    override fun save() {
        presenter.saveState(State(swipe, scoreVal, moveVal, bestVal, array))
    }

    override fun onPause() {
        save()
        super.onPause()
    }
}