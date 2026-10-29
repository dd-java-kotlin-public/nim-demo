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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertFailsWith

/**
 * Unit test specifications following Uncle Bob's "One Concept Per Test" rule.
 * Tests act as immutable behavioral contracts.
 */
class SimpleGameEngineTest {

    private val engine: GameEngine = SimpleGameEngine()

    @Test
    fun `applyMove with valid sticksTaken reduces stickCount and swaps currentPlayer`() {
        val initial = GameState(remainingSticks = 21, currentPlayer = 1)
        val updated = engine.applyMove(initial, sticksTaken = 3)

        assertAll(
            { assertEquals(18, updated.remainingSticks) },
            { assertEquals(2, updated.currentPlayer) },
        )
    }

    @Test
    fun `applyMove throws IllegalArgumentException on zero sticksTaken`() {
        val state = GameState(remainingSticks = 21, currentPlayer = 1)

        assertFailsWith<IllegalArgumentException> {
            engine.applyMove(state, sticksTaken = 0)
        }
    }

    @Test
    fun `applyMove throws IllegalArgumentException on more than 3 sticksTaken`() {
        val state = GameState(remainingSticks = 21, currentPlayer = 1)

        assertFailsWith<IllegalArgumentException> {
            engine.applyMove(state, sticksTaken = 4)
        }
    }

    @Test
    fun `applyMove throws IllegalArgumentException when sticksTaken exceeds remainingSticks`() {
        val state = GameState(remainingSticks = 2, currentPlayer = 1)

        assertFailsWith<IllegalArgumentException> {
            engine.applyMove(state, sticksTaken = 3)
        }
    }

    @Test
    fun `applyMove creates GameState with isGameOver == true and loser == player making the move when last stick is taken`() {
        val state = GameState(remainingSticks = 1, currentPlayer = 1)
        val result = engine.applyMove(state, sticksTaken = 1)

        assertTrue(result.isGameOver)
        assertEquals(1, result.loser)
    }

    @Test
    fun `applyMove throws IllegalStateException when isGameOver == true`() {
        val finishedState =
            GameState(remainingSticks = 0, currentPlayer = 1, isGameOver = true, loser = 1)

        assertFailsWith<IllegalStateException> {
            engine.applyMove(finishedState, sticksTaken = 1)
        }
    }
}
