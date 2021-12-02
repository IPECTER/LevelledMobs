/*
 * Copyright (c) 2020-2021  lokka30. Use of this source code is governed by the GNU AGPL v3.0 license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.levelling.strategy;

import me.lokka30.levelledmobs.LevelledMobs;
import me.lokka30.levelledmobs.levelling.LevelledMob;

/**
 * This class generates a level for a mob
 * based upon their location relative to
 * the world's spawnpoint OR a specific set
 * of coordinates provided by the strategy's
 * configuration in the Rules system.
 *
 * @author lokka30
 * @see LevellingStrategy
 * @since v4.0.0
 */
public class SpawnDistanceLevellingStrategy implements LevellingStrategy {

    @Override
    public int calculateLevel(LevelledMobs main, LevelledMob mob) {
        //TODO
        return -1;
    }
}
