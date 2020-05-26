package ml

import core.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.lang.Exception
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.math.roundToInt
import kotlin.random.Random

class GeneticTesting {

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    val vecSize = 30
    val bestSize = 4
    val randomAdd = 3
    val genSize = bestSize * (bestSize - 1) / 2 + bestSize + randomAdd

    val baseVec: IntArray = arrayOf(3, -3, 30, -30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).toIntArray()

    inner class Generation() {
        var generation = ArrayList<Pair<IntArray, Int>>().toMutableList()

        operator fun get(index: Int): IntArray {
            return generation[index].first
        }

        fun genFirstGen() {
            for (i in 0 until genSize - 1) {
                val vec = IntArray(vecSize)
                for (j in 0 until vecSize) {
                    vec[j] = Random.nextInt(-50, 50)
                }
                generation.add(vec to 0)
            }
            generation.add(baseVec to 0)
        }

        fun gen (prevGeneration: Generation) {
            for (i in 0 until bestSize) {
                generation.add(prevGeneration[i] to 0)
                for (j in i + 1 until bestSize) {
                    generation.add(mutate(prevGeneration[i], prevGeneration[j]) to 0)
                }
            }
            for (i in 0 until randomAdd) {
                val vec = IntArray(vecSize)
                for (j in 0 until vecSize) {
                    vec[j] = Random.nextInt(-50, 50)
                }
                generation.add(vec to 0)
            }
        }

        private fun mutate(vec1: IntArray, vec2: IntArray): IntArray {
            val newVec = IntArray(vecSize)
            for (i in 0 until vecSize) {
                val d = Random.nextDouble(0.33, 0.95)
                newVec[i] = (d * vec1[i].toDouble() + (1.0-d) * vec2[i].toDouble()).roundToInt()
            }
            return newVec
        }

        fun sort() {
            generation.sortBy { it -> -it.second }
        }
    }

    fun test() {
        val gen = Generation()
        gen.genFirstGen()
        val gen2 = Generation()
        gen2.gen(gen)
        println(gen.generation.size)
        println(gen2.generation.size)
        println(genSize)
    }

    fun start() {
        runBlocking {
            var genNumber = 0
            var generation = Generation()
            generation.genFirstGen()
            val list = ArrayList<Job>()
            while (true) {
                for (i in 0 until genSize) {
                    for (j in 0 until genSize) {
                        if (i == j)
                            continue
                        val tg: TestingGame = TestingGame(CheckersModel(), MinimaxAlphaBetaPlayerWithVector(i.toString(), Color.WHITE, 6, generation[i]), MinimaxAlphaBetaPlayerWithVector(j.toString(), Color.BLACK, 6, generation[j]))
                        list.add(GlobalScope.launch {
                            try {
                                tg.start()
                            } catch (e: Exception) {
                            }
                            when (tg.getModel().gameState) {
                                GameState.DRAW -> {
                                    generation.generation[i] = generation[i] to generation.generation[i].second + 1
                                    generation.generation[j] = generation[j] to generation.generation[j].second + 1
                                    logger.log(Level.WARNING, "Game $i - $j ended: DRAW")
                                }
                                GameState.WHITE_WINS -> {
                                    generation.generation[i] = generation[i] to generation.generation[i].second + 2
                                    logger.log(Level.WARNING, "Game $i - $j ended: WHITE WINS")
                                }
                                GameState.BLACK_WINS -> {
                                    generation.generation[j] = generation[j] to generation.generation[j].second + 2
                                    logger.log(Level.WARNING, "Game $i - $j ended: BLACK WINS")
                                }
                                else -> {
                                    logger.log(Level.WARNING, "Error: Game isn't ended")
                                }
                            }
                        })
                    }
                }
                for (job in list) {
                    job.join()
                }
                generation.sort()

                logger.log(Level.INFO, "Start writing file")
                val file = File("$genNumber.txt").printWriter()
                for (i in 0 until genSize) {
                    file.print(generation.generation[i].second.toString() + " :: ")
                    for (j in 0 until vecSize) {
                        file.print(generation[i][j].toString() + ", ")
                    }
                    file.println()
                }
                file.close()
                genNumber++

                val newGeneration = Generation()
                newGeneration.gen(generation)
                generation = newGeneration
            }
        }
    }
}