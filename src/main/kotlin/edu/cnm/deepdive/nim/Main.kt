/*
 *  Copyright 2026 CNM Ingenuity, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cnm.deepdive.nim

import edu.cnm.deepdive.nim.model.GameState
import edu.cnm.deepdive.nim.service.GameEngine
import edu.cnm.deepdive.nim.service.SimpleGameEngine

/**
 * Entry-point function of the 21 Sticks console-mode game. When invoked, this function choreographs
 * user console I/O and the game logic to prompt two players through a single game of 21 Sticks.
 *
 * No arguments are read from the command line.
 */
fun main() {
    renderInstructions()
    val state = play(SimpleGameEngine())
    if (state.isGameOver) {
        renderOutcome(state)
    }
}

private fun renderInstructions() {
    println("=========================================")
    println("   The Game of Nim (21 Sticks)")
    println("=========================================")
    println("Rules: Take 1, 2, or 3 sticks per turn.")
    println("The player forced to take the LAST stick loses!")
}

private fun play(engine: GameEngine): GameState {
    var state = GameState(remainingSticks = 21, currentPlayer = 1)
    while (!state.isGameOver) {
        renderBoard(state)
        val input = promptForMove(state)
        if (input == null) {
            println("Invalid input. Please enter a valid number (1, 2, or 3).\n")
            continue
        }
        try {
            state = engine.applyMove(state, input)
        } catch (ex: IllegalArgumentException) {
            println("Illegal move: ${ex.message}\n")
        } catch (ex: NotImplementedError) {
            System.err.println("No game engine: ${ex.message}")
            break
        }
    }
    return state
}

private fun renderBoard(state: GameState) {
    val sticks = "| ".repeat(state.remainingSticks)
    println("\nSticks remaining (${state.remainingSticks}):")
    println(sticks)
    println("-".repeat(state.remainingSticks * 2))
}

private fun promptForMove(state: GameState): Int? {
    print("Player ${state.currentPlayer}, take sticks (1-3): ")
    return readlnOrNull()?.trim()?.toIntOrNull()
}

private fun renderOutcome(state: GameState) {
    println("\n=========================================")
    println("Game over! Player ${state.loser} took the last stick and loses.")
    println("Player ${if (state.loser == 1) 2 else 1} wins!")
    println("=========================================")
}
