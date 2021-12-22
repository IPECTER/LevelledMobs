/*
 * Copyright (c) 2020-2021  lokka30. Use of this source code is governed by the GNU AGPL v3.0 license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.file.external.customdrops;

import org.bukkit.inventory.ItemFlag;

import java.util.Optional;

public record CustomDropOptions(
        Optional<Float>         chanceRequirement,
        Optional<Float>         overallChanceRequirement,
        Optional<Integer>       minLevelRequirement,
        Optional<Integer>       maxLevelRequirement,
        Optional<Integer>       amount,
        Optional<Float>         equippedChance,
        Optional<Integer>       customModelData,
        Optional<ItemFlag[]>    itemFlags,
        Optional<String>        nbtData,
        Optional<Boolean>       removeVanillaDrops,
        Optional<Boolean>       noMultiplier,
        Optional<Boolean>       onlyDropItemsIfEquipped,
        Optional<Integer>       groupId,
        Optional<Integer>       maxDropGroup,
        Optional<Integer>       priority
) { }
