package uz.gita.game_2048_lazizjon.data.repository.impl

import uz.gita.game_2048_lazizjon.data.local.sharedPref.SharedPref
import uz.gita.game_2048_lazizjon.data.model.State
import uz.gita.game_2048_lazizjon.data.repository.AppRepository
import kotlin.random.Random


class AppRepositoryImpl private constructor() : AppRepository {

    companion object {
        private lateinit var obj: AppRepository
        private lateinit var sharedPref: SharedPref

        fun init() {
            if (::obj.isInitialized) return
            obj = AppRepositoryImpl()
            sharedPref = SharedPref.get()
        }

        fun getAppRepository(): AppRepository = obj
    }

    private lateinit var matrix: Array<IntArray>
    private var intervalOf4 = 0
    private var addElement = 2

    override fun isEmptyCell(): Boolean {
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                if (matrix[i][j] == 0)
                    return true
            }
        }
        return false
    }

    override fun addNewElementToMatrix() {
        val coordinates = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                if (matrix[i][j] == 0) {
                    coordinates.add(Pair(i, j))
                }
            }
        }
        val randomIndex = Random.nextInt(0, coordinates.size)
        if(intervalOf4 == 20) {
            addElement = 4
            intervalOf4 = Random.nextInt(0, 18)
        }
        matrix[coordinates[randomIndex].first][coordinates[randomIndex].second] = addElement
        addElement = 2
        intervalOf4 ++
    }

    override fun getMatrixData(): Array<IntArray> = matrix

    override fun moveLeft(): Int {
        var score = 0
        var isChanged = false
        var zero: Boolean

        for (i in matrix.indices) {
            val temp = ArrayList<Int>()
            var bool = true
            zero = false
            for (j in matrix.indices) {
                if (matrix[i][j] == 0) {
                    zero = true
                    continue
                }
                if (zero)
                    isChanged = true
                if (temp.isEmpty()) temp.add(matrix[i][j])
                else {
                    if (temp.last() == matrix[i][j] && bool) {
                        score += temp.last() * 2
                        temp[temp.size - 1] = temp.last() * 2
                        isChanged = true
                        bool = false
                    } else {
                        bool = true
                        temp.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }
            for (j in 0 until temp.size) {
                matrix[i][j] = temp[j]
            }
        }

        if (isChanged)
            return score
        return -1
    }

    override fun moveRight(): Int {
        var score = 0
        var zero: Boolean
        var isChanged = false

        for (i in matrix.indices) {
            val temp = ArrayList<Int>()
            var bool = true
            zero = false
            for (j in matrix.indices) {
                if (matrix[i][3 - j] == 0) {
                    zero = true
                    continue
                }
                if (zero)
                    isChanged = true
                if (temp.isEmpty()) temp.add(matrix[i][3 - j])
                else {
                    if (temp.last() == matrix[i][3 - j] && bool) {
                        score += temp.last() * 2
                        temp[temp.size - 1] = temp.last() * 2
                        isChanged = true
                        bool = false
                    } else {
                        bool = true
                        temp.add(matrix[i][3 - j])
                    }
                }
                matrix[i][3 - j] = 0
            }
            for (j in temp.indices) {
                matrix[i][3 - j] = temp[j]
            }
        }
        if (isChanged)
            return score
        return -1
    }

    override fun moveUp(): Int {
        var score = 0
        var isChanged = false
        var zero: Boolean

        for (j in matrix.indices) {
            val temp = ArrayList<Int>()
            var bool = true
            zero = false
            for (i in matrix.indices) {
                if (matrix[i][j] == 0) {
                    zero = true
                    continue
                }
                if (zero)
                    isChanged = true
                if (temp.isEmpty()) temp.add(matrix[i][j])
                else {
                    if (temp.last() == matrix[i][j] && bool) {
                        score += temp.last() * 2
                        temp[temp.size - 1] = temp.last() * 2
                        isChanged = true
                        bool = false
                    } else {
                        bool = true
                        temp.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }
            for (i in temp.indices) {
                matrix[i][j] = temp[i]
            }
        }
        if (isChanged)
            return score
        return -1
    }

    override fun moveDown(): Int {
        var score = 0
        var isChanged = false
        var zero: Boolean

        for (j in matrix.indices) {
            val temp = ArrayList<Int>()
            var bool = true
            zero = false
            for (i in matrix.indices) {
                if (matrix[3 - i][j] == 0) {
                    zero = true
                    continue
                }
                if (zero)
                    isChanged = true
                if (temp.isEmpty()) temp.add(matrix[3 - i][j])
                else {
                    if (temp.last() == matrix[3 - i][j] && bool) {
                        score += temp.last() * 2
                        temp[temp.size - 1] = temp.last() * 2
                        bool = false
                        isChanged = true
                    } else {
                        bool = true
                        temp.add(matrix[3 - i][j])
                    }
                }
                matrix[3 - i][j] = 0
            }
            for (i in temp.indices) {
                matrix[3 - i][j] = temp[i]
            }
        }
        if (isChanged)
            return score
        return -1
    }

    override fun saveRecord(rec: Int) {
        sharedPref.saveRecord(rec)
    }

    override fun saveState(state: State) {
        sharedPref.saveState(state)
    }

    override fun getState(): State = with(sharedPref) {
        val state = getState()
        matrix = state.array
        if (!sharedPref.isFirst())
            return state
        addNewElementToMatrix()
        addNewElementToMatrix()
        return state
    }

    override fun getNewArray(): Array<IntArray> {
        for(i in 0..3) {
            for(j in 0..3) {
                matrix[i][j] = 0
            }
        }
        addNewElementToMatrix()
        addNewElementToMatrix()
        return matrix
    }
}

/*
    override fun moveLeft() {
        matrixChecker =  matrix

        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in 0 until matrix[i].size) { //
                if (matrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[i][j])
                else {
                    if (amount.last() == matrix[i][j] && bool) {
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        amount.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }
            for (j in 0 until amount.size) {
                matrix[i][j] = amount[j]
            }
        }
        addNewElementToMatrix()
    }

    override fun moveRight() {
        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[i][j])
                else {
                    if (amount.last() == matrix[i][j] && bool) {
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }
            for (k in 0 until amount.size) {
                matrix[i][3 - k] = amount[k]
            }
        }
        addNewElementToMatrix()
    }

    override fun moveUp() {
        for (j in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (i in 0 until matrix[j].size) {
                if (matrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[i][j])
                else {
                    if (amount.last() == matrix[i][j] && bool) {
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        amount.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }
            for (k in 0 until amount.size) {
                matrix[k][j] = amount[k]
            }
        }
        addNewElementToMatrix()
    }

    override fun moveDown() {
        for (j in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (i in matrix.size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[i][j])
                else {
                    if (amount.last() == matrix[i][j] && bool) {
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }

            for (k in 0 until amount.size) {
                matrix[3 - k][j] = amount[k]
            }
        }
        addNewElementToMatrix()
    }

    private fun addNewElementToMatrix() {
        val coordinates = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in 0 until (matrix[i].size)) {
                if (matrix[i][j] == 0) {
                    coordinates.add(Pair(i, j))
                }
            }
        }
        val randomIndex = Random().nextInt(coordinates.size)
        matrix[coordinates[randomIndex].first][coordinates[randomIndex].second] = ADD_ELEMENT
    }*/