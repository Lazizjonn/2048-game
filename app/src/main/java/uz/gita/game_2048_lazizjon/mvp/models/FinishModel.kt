package uz.gita.game_2048_lazizjon.mvp.models

import uz.gita.game_2048_lazizjon.data.model.State
import uz.gita.game_2048_lazizjon.data.repository.impl.AppRepositoryImpl
import uz.gita.game_2048_lazizjon.mvp.contracts.FinishContract

class FinishModel : FinishContract.Model {
    private val repository = AppRepositoryImpl.getAppRepository()

    override fun getState(): State = repository.getState()
    override fun getNewArray(): Array<IntArray> = repository.getNewArray()
    override fun saveState(state: State) = repository.saveState(state)

}