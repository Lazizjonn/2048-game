package uz.gita.game_2048_lazizjon.mvp.presenters

import uz.gita.game_2048_lazizjon.data.model.State
import uz.gita.game_2048_lazizjon.mvp.contracts.FinishContract
import uz.gita.game_2048_lazizjon.mvp.models.FinishModel

class FinishPresenter : FinishContract.Presenter {
    private val model: FinishContract.Model = FinishModel()

    override fun getState(): State = model.getState()
    override fun getNewArray(): Array<IntArray> = model.getNewArray()
    override fun saveState(state: State) = model.saveState(state)

}