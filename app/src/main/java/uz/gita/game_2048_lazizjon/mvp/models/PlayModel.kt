package uz.gita.game_2048_lazizjon.mvp.models

import uz.gita.game_2048_lazizjon.data.model.State
import uz.gita.game_2048_lazizjon.data.repository.AppRepository
import uz.gita.game_2048_lazizjon.data.repository.impl.AppRepositoryImpl
import uz.gita.game_2048_lazizjon.mvp.contracts.PlayContract

class PlayModel : PlayContract.Model {
    private val repository: AppRepository = AppRepositoryImpl.getAppRepository()

    override fun addNewElementToMatrix() = repository.addNewElementToMatrix()
    override fun isEmptyCell(): Boolean = repository.isEmptyCell()
    override fun getMatrixData(): Array<IntArray> = repository.getMatrixData()
    override fun moveLeft(): Int = repository.moveLeft()
    override fun moveRight(): Int = repository.moveRight()
    override fun moveUp(): Int = repository.moveUp()
    override fun moveDown(): Int = repository.moveDown()
    override fun saveRecord(rec: Int) = repository.saveRecord(rec)
    override fun saveState(state: State) = repository.saveState(state)
    override fun getState(): State = repository.getState()
    override fun getNewArray(): Array<IntArray> = repository.getNewArray()
}