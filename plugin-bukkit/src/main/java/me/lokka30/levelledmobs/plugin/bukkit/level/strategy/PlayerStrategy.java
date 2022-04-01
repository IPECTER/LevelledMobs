/*
 * This file is Copyright (c) 2020-2022 lokka30.
 * This file is/was present in the LevelledMobs resource.
 * Repository: <https://github.com/lokka30/LevelledMobs>
 * Use of this source code is governed by the GNU GPL v3.0
 * license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.plugin.bukkit.level.strategy;

import me.lokka30.levelledmobs.plugin.bukkit.LevelledMobs;
import me.lokka30.levelledmobs.plugin.bukkit.level.LevelledMob;
import org.jetbrains.annotations.NotNull;

/**
 * TODO document this class.
 */
public record PlayerStrategy(
    int test //TODO
) implements LevellingStrategy {

    @Override
    @NotNull
    public String getName() {
        return "Player";
    }

    @Override
    public int calculateLevel(@NotNull LevelledMobs main, @NotNull LevelledMob mob) {
        return 0;
    }
}