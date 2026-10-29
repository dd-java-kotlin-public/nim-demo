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
package edu.cnm.deepdive.nim.service

import edu.cnm.deepdive.nim.model.GameState

/**
 * Immutable interface defining the core business logic of the 21 Sticks variation of Nim.
 * This interface, along with the included KDoc comments, articulates the technical and behavioral
 * contracts that an implementation must satisfy to enforce the domain rules.
 */
interface GameEngine {

    /**
     * Executes a move for the current player. To do this, a new instance of [GameState] is created
     * and returned; this instance is based on [currentState], with [sticksTaken] removed from
     * [remainingSticks][GameState.remainingSticks], and with the
     * [currentPlayer][GameState.currentPlayer] swapped to the opposing player&mdash;unless
     * [sticksTaken] reduces [remainingSticks][GameState.remainingSticks] to zero (`0`); in that
     * case, the new [GameState] instance has [isGameOver][GameState.isGameOver] set to `true`,
     * [currentPlayer][GameState.currentPlayer] set to `null`, and [loser][GameState.loser] set to
     * the numeric value representing the player making the move in this invocation.
     *
     * @param currentState Current state of the game.
     * @param sticksTaken Number of sticks to remove in this move (must be 1, 2, or 3, and must not
     * exceed [currentState.remainingSticks][GameState.remainingSticks].
     *
     * @return New immutable [GameState], based on [currentState] with [sticksTaken] sticks removed.
     * @throws IllegalArgumentException if [sticksTaken] is out of bounds or exceeds 
     * [currentState.remainingSticks][GameState.remainingSticks].
     * @throws IllegalStateException if the game is already over.
     */
    fun applyMove(currentState: GameState, sticksTaken: Int): GameState

}
