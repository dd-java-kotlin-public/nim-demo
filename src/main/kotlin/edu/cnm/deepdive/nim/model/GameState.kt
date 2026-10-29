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
package edu.cnm.deepdive.nim.model

/**
 * Represents the state of a game of 21 Sticks at some moment in the game. The contents of this
 * class are immutable, so a new instance of [GameState] must be created as a result of each move
 * made by either player.
 *
 * @param remainingSticks number of sticks remaining in the pile.
 * @param currentPlayer player to move next (`1` or `2`, representing player1 or player 2,
 * respectively), or `null` if the game is over.
 * @param isGameOver `true` if all sticks have been removed (i.e., if `remainingSticks == 0`).
 * @param loser last player (`1` or `2`, representing player1 or player 2, respectively) that moved
 * last in a completed game, or `null` if the game is not yet over.
 */
data class GameState(
    val remainingSticks: Int,
    val currentPlayer: Int?,
    val isGameOver: Boolean = false,
    val loser: Int? = null,
)
