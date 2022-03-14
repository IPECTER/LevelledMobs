/*
 * This file is Copyright (c) 2020-2022 lokka30.
 * This file is/was present in the LevelledMobs resource.
 * Repository: <https://github.com/lokka30/LevelledMobs>
 * Use of this source code is governed by the GNU GPL v3.0
 * license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.integration.internal;

import me.lokka30.levelledmobs.integration.Integration;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * A LevelledMobs integration. Plugin:     NBT API Author:     tr7zw Link:
 * https://www.spigotmc.org/resources/nbt-api.7939/
 *
 * @author lokka30
 * @since 4.0.0
 */
public class NBTAPIIntegration implements Integration {

    @Override
    @NotNull
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isInstalled() {
        assert isInstalled();
        return Bukkit.getPluginManager().isPluginEnabled("NBTAPI");
    }

}