package uz.gita.game_2048_lazizjon.data.repository

import uz.gita.game_2048_lazizjon.data.model.State

interface AppRepository {

    fun addNewElementToMatrix()
    fun isEmptyCell(): Boolean
    fun getMatrixData(): Array<IntArray>
    fun moveLeft(): Int
    fun moveRight(): Int
    fun moveUp(): Int
    fun moveDown(): Int
    fun saveRecord(rec: Int)
    fun saveState(state: State)
    fun getState(): State
    fun getNewArray(): Array<IntArray>
}