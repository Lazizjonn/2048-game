package uz.gita.game_2048_lazizjon.mvp.contracts

import uz.gita.game_2048_lazizjon.data.model.State

interface PlayContract {
    interface Model {
        fun addNewElementToMatrix()
        fun isEmptyCell(): Boolean
        fun getMatrixData(): Array<IntArray>
        fun moveLeft(): Int
        fun moveRight(): Int
        fun moveUp(): Int
        fun moveDown(): Int
        fun saveRecord(rec: Int)
        fun saveState(state: State)
        fun getState() : State
        fun getNewArray(): Array<IntArray>
    }

    interface View {
        fun illustrateMatrix(matrix: Array<IntArray>)
        fun finishScreen()
        fun setSwipeEverywhere(ok: Boolean)
        fun loadData(state: State)
        fun addMove(extra: Int)
        fun save()
    }

    interface Presenter {
        fun saveRecord(record: Int)
        fun checkMove(score: Int)
        fun swipeSideLeft()
        fun swipeSideRight()
        fun swipeSideUp()
        fun swipeSideDown()
        fun saveState(state: State)
        fun loadData()
        fun restart()
    }
}