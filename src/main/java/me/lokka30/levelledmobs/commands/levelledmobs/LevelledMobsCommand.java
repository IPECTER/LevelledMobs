/*
 * Copyright (c) 2020-2021  lokka30. Use of this source code is governed by the GNU AGPL v3.0 license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.commands.levelledmobs;

import me.lokka30.levelledmobs.LevelledMobs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lokka30
 * @see TabExecutor
 * @see me.lokka30.levelledmobs.handlers.CommandHandler
 * @since v4.0.0
 * This class handles execution of the
 * `/levelledmobs` command.
 */
public class LevelledMobsCommand implements TabExecutor {

    private final LevelledMobs main;

    public LevelledMobsCommand(final LevelledMobs main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        /*
        TODO
            lokka30: Complete method body.
         */
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        /*
        TODO
            lokka30: Complete method body.
         */
        return new ArrayList<>();
    }
}