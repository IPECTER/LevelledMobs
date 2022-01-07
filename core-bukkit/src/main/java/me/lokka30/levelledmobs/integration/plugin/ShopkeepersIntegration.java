/*
 * This file is Copyright (c) 2020-2022 lokka30.
 * This file is/was present in the LevelledMobs resource.
 * Repository: <https://github.com/lokka30/LevelledMobs>
 * Use of this source code is governed by the GNU GPL v3.0
 * license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.integration.plugin;

import me.lokka30.levelledmobs.LevelledMobs;
import me.lokka30.levelledmobs.integration.Integration;
import me.lokka30.levelledmobs.integration.MobOwner;
import me.lokka30.levelledmobs.levelling.LevelledMob;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * A LevelledMobs integration.
 * Plugin:     Shopkeepers
 * Author:     blablubbabc
 * Link:       https://www.spigotmc.org/resources/shopkeepers.80756/
 *
 * @author lokka30
 * @since v4.0.0
 */
public class ShopkeepersIntegration implements Integration, MobOwner {

    private @NotNull final LevelledMobs main;
    public ShopkeepersIntegration(final @NotNull LevelledMobs main) { this.main = main; }

    @Override
    @NotNull
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isInstalled() {
        return Bukkit.getPluginManager().isPluginEnabled("Shopkeepers");
    }

    @Override
    public boolean isMobOwner(@NotNull LevelledMob mob) {
        assert isEnabled(main);
        // TODO
        return false;
    }

}