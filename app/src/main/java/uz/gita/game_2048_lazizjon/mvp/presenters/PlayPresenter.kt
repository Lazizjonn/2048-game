package uz.gita.game_2048_lazizjon.mvp.presenters

import uz.gita.game_2048_lazizjon.data.model.State
import uz.gita.game_2048_lazizjon.mvp.contracts.PlayContract
import uz.gita.game_2048_lazizjon.mvp.models.PlayModel


class PlayPresenter(private val view: PlayContract.View) : PlayContract.Presenter {
    private val model: PlayContract.Model = PlayModel()

    override fun checkMove(score: Int) {
        if (score >= 0) {
            if (model.isEmptyCell()) {
                model.addNewElementToMatrix()
                view.addMove(score)
                view.illustrateMatrix(model.getMatrixData())
            }

            if (!model.isEmptyCell()) {
                val matrix = model.getMatrixData()
                for (i in 0..2) {
                    for (j in 0..2) {
                        if (matrix[i][j] == matrix[i + 1][j] || matrix[i][j] == matrix[i][j + 1])
                            return
                    }
                }
                for(i in 0..2)
                    if(matrix[3][i] == matrix[3][i + 1])
                        return
                for(i in 0..2)
                    if(matrix[i][3] == matrix[i + 1][3])
                        return
                view.save()
                view.finishScreen()
            }
        }
    }
    override fun swipeSideLeft() {
        checkMove(model.moveLeft())
    }
    override fun swipeSideRight() {
        checkMove(model.moveRight())
    }
    override fun swipeSideUp() {
        checkMove(model.moveUp())
    }
    override fun swipeSideDown() {
        checkMove(model.moveDown())
    }
    override fun saveState(state: State) {
        model.saveState(state)
    }
    override fun saveRecord(record: Int) {
        model.saveRecord(record)
    }
    override fun loadData() = with(model.getState()) {
        view.loadData(this)
    }
    override fun restart() {
        view.loadData(State(model.getState().swipe, 0, 0, model.getState().record, model.getNewArray()))
    }

}