/*
 * This file is Copyright (c) 2020-2022 lokka30.
 * This file is/was present in the LevelledMobs resource.
 * Repository: <https://github.com/lokka30/LevelledMobs>
 * Use of this source code is governed by the GNU GPL v3.0
 * license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.command.levelledmobs.subcommand.compatibility;

import me.lokka30.levelledmobs.LevelledMobs;
import me.lokka30.levelledmobs.command.CommandHandler;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lokka30
 * @since v4.0.0
 * This is a subcommand of the '/levelledmobs' command.
 * This subcommand allows users to re-run the LM compat checker and view its results.
 * @see me.lokka30.levelledmobs.command.levelledmobs.LevelledMobsCommand
 * @see CommandHandler
 */
public class CompatibilitySubcommand implements CommandHandler.Subcommand {

    /*
    TODO LIST:
        - Complete method body for the run method.
        - Test if the logic is working correctly.
        - Add customisable messages to the run method.
        - Test if the customisable messages are working correctly.
     */

    final HashSet<String> labels = new HashSet<>(Set.of("COMPATIBILITY", "COMPAT"));

    @Override
    public @NotNull String getMainLabel() {
        return "compatibility";
    }

    @Override
    public @NotNull HashSet<String> getLabels() {
        return labels;
    }

    @Override
    public void run(@NotNull LevelledMobs main, @NotNull CommandSender sender, @NotNull String baseCommandLabel, @NotNull String subCommandLabel, @NotNull String[] args) {
        // TODO complete method body.
        sender.sendMessage("The compatibility subcommand is work-in-progress.");
    }
}