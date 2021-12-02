/*
 * Copyright (c) 2020-2021  lokka30. Use of this source code is governed by the GNU AGPL v3.0 license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.integration;

import me.lokka30.levelledmobs.LevelledMobs;
import org.jetbrains.annotations.NotNull;

/**
 * This interface is used by all Integrations,
 * containing common methods used across all of them.
 *
 * @author lokka30
 * @since v4.0.0
 */
public interface Integration {

    /**
     * Get the name of the Integration.
     *
     * @return the name of the class providing the integration, e.g. {@code CitizensIntegration}.
     * @since v4.0.0
     */
    @NotNull
    String getName();

    /**
     * Check if the plugin being integrated with is installed.
     *
     * @return if the plugin being integrated with is installed.
     * @since v4.0.0
     */
    boolean isInstalled();

    /**
     * Note: integrations can be user-disabled in advanced.yml - see 'disabled-integrations'.
     *
     * @return if the integration is enabled.
     * @since v4.0.0
     */
    default boolean isEnabled(@NotNull final LevelledMobs main) {
        //noinspection ConstantConditions
        return !main.fileHandler.getAdvancedCfg().getStringList("disabled-integrations").contains(getName()) && isInstalled();
    }
}