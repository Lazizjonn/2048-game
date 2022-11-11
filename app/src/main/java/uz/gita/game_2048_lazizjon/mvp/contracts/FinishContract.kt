package uz.gita.game_2048_lazizjon.mvp.contracts

import uz.gita.game_2048_lazizjon.data.model.State

interface FinishContract {
    interface Model {
        fun getState(): State
        fun getNewArray(): Array<IntArray>
        fun saveState(state: State)
    }

    interface Presenter {
        fun getState(): State
        fun getNewArray(): Array<IntArray>
        fun saveState(state: State)
    }

    interface View {
        fun loadViews()
        fun loadData()
        fun goBack()
        fun restart()
    }
}