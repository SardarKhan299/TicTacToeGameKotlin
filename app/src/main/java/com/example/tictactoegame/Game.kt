package com.example.tictactoegame

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.constraintlayout.helper.widget.MotionPlaceholder
import kotlin.math.ceil
import kotlin.system.exitProcess


data class Player(val name:String="Computer",val symbol:Char = 'O'){
    init {
        println("Init data class")
    }
}
sealed class Status(){
    object Idle:Status()
    object Running:Status()
    object GameOver:Status()
}
sealed class Cell(val placeholder:Char){
    object Empty:Cell('_')
    data class Filled(val player: Player):Cell(player.symbol)
}
class Game {

    private var board = MutableList<Cell>(9){Cell.Empty}
    private var status:Status = Status.Idle
    private lateinit var player:Player

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    public fun start(){
        status = Status.Running
        println("start: Welcome to game ")
        println("start: Pick a number from 0-8")
        print("Enter your name: ")
        getName()
        while (status is Status.Running){
            getCell()
        }
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCell() {
        print("")
        var input = readLine()
        try {
            require(input != null)
            val cellNumber = input.toInt()
            require(cellNumber in 0..8 )
            setCell(cellNumber)
        }catch (e:Throwable){
            println("Invalid number ${e.message}")
        }
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setCell(cellNumber:Int){
        val cell = board[cellNumber]
        if(cell is Cell.Empty){
            board.set(cellNumber,Cell.Filled(player))
            checkBoard()
            generateComputerMove()
            printBoard()
        }else{
            println("Cell taken choose another")
        }
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkBoard() {
        val winningCombinations = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )
        val player1Cells = mutableListOf<Int>()
        val player2Cells = mutableListOf<Int>()
        board.forEachIndexed { index, cell ->
            if(cell.placeholder == 'X'){
                player1Cells.add(index)
            }
            if(cell.placeholder == 'O'){
                player2Cells.add(index)
            }
            println("Your moves $player1Cells")
            println("Computer moves $player2Cells")

            kotlin.run combinationLoop@ {
               winningCombinations.forEach { combination ->
                    if (player1Cells.containsAll(combination)) {
                        won()
                        return@combinationLoop
                    }

                    if (player2Cells.containsAll(combination)) {
                        lost()
                        return@combinationLoop
                    }

                }
            }
            if (board.none { it is Cell.Empty } && status is Status.Running) {
                draw()
            }
            if(status is Status.GameOver) {
                finish()
                playAgain()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun finish() {
        status = Status.Idle
        board.replaceAll {
            Cell.Empty
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalStdlibApi
    private fun playAgain() {
        print("Do you wish to play another one? Y/N: ")
        val input = readLine()
        try {
            require(value = input != null)
            val capitalizedInput = input.replaceFirstChar(Char::titlecase)
            val positive = capitalizedInput.contains(other = "Y")
            val negative = capitalizedInput.contains(other = "N")
            require(value = positive || negative)
            if (positive)
                start()
            else if (negative)
                exitProcess(status = 0)
        } catch (e: IllegalArgumentException) {
            println("Wrong option. Type either 'Y' or 'N'")
            playAgain()
        }
    }

    private fun won(){
        status = Status.GameOver
        printBoard()
        println("Congratulations, ${player.name} , You won!!! ")
    }

    private fun lost(){
        status = Status.GameOver
        printBoard()
        println("Sorry, ${player.name} , You Lost!!! ")
    }

    private fun draw(){
        status = Status.GameOver
        printBoard()
        println("Its a Draw ")
    }
    fun generateComputerMove(){
        try {
            val availableCells = mutableListOf<Int>()
            board.forEachIndexed { index, cell ->
                if(cell is Cell.Empty){
                    availableCells.add(index)
                }
            }
            if(availableCells.isNotEmpty()){
                var randomCell =  availableCells.random()
                board.set(randomCell,Cell.Filled(Player()))
            }
        }catch (e:Throwable){
            print("Error ${e.message}")
            e.printStackTrace()
        }
    }

    fun getName(){
        try {
            val name = readLine()
            require(name != null)
            player = Player(name,'X')
            print("Its your move ${player.name}")
            printBoard()
        }catch (e:Throwable){
            println("Invalid game${e.message}")
            e.printStackTrace()
        }
    }
    fun printBoard(){
        println()
        println(" ------")
        println("| ${board[0].placeholder} ${board[1].placeholder} ${board[2].placeholder} |")
        println("| ${board[3].placeholder} ${board[4].placeholder} ${board[5].placeholder} |")
        println("| ${board[6].placeholder} ${board[7].placeholder} ${board[8].placeholder} |")
        println(" ------")
        println()
    }
}

@ExperimentalStdlibApi
@RequiresApi(Build.VERSION_CODES.N)
fun main(){
    val game = Game()
    game.start()
}