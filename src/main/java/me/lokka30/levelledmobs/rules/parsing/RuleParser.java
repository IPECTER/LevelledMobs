/*
 * This file is Copyright (c) 2020-2022 lokka30.
 * This file is/was present in the LevelledMobs resource.
 * Repository: <https://github.com/lokka30/LevelledMobs>
 * Use of this source code is governed by the GNU GPL v3.0
 * license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.rules.parsing;

import me.lokka30.levelledmobs.LevelledMobs;
import me.lokka30.levelledmobs.rules.Group;
import me.lokka30.levelledmobs.rules.Rule;
import me.lokka30.levelledmobs.rules.RuleListener;
import me.lokka30.levelledmobs.util.Utils;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

/**
 * @author lokka30
 * @since v4.0.0
 * This class parses rules from the Rules configuration
 * into Rule objects that are accessed by the plugin.
 */
public class RuleParser {

    private final LevelledMobs main;
    public RuleParser(final @NotNull LevelledMobs main) {
        this.main = main;
    }

    private final HashSet<Group<EntityType>> mobGroups = new HashSet<>();
    public @NotNull HashSet<Group<EntityType>> getMobGroups() { return mobGroups; }

    private final HashSet<Group<Biome>> biomeGroups = new HashSet<>();
    public @NotNull HashSet<Group<Biome>> getBiomeGroups() { return biomeGroups; }

    private final HashSet<RuleListener> ruleListeners = new HashSet<>();
    public @NotNull HashSet<RuleListener> getRuleListeners() { return ruleListeners; }

    private final HashSet<Rule> presets = new HashSet<>();
    public @NotNull HashSet<Rule> getPresets() { return presets; }

    public void parse() {
        clearCache();

        addRuleGroups();
        addRulePresets();
        addRuleListeners();
    }

    void clearCache() {
        getRuleListeners().clear();
        getPresets().clear();
    }

    void addRuleGroups() {
        addMobRuleGroups();
        addBiomeRuleGroups();
    }

    void addMobRuleGroups() {
        for(
                String mobGroupName : main.getFileHandler().getGroupsFile().getData()
                .getSection("mob-groups").singleLayerKeySet()
        ) {
            HashSet<EntityType> entityTypes = new HashSet<>();

            for(
                    String entityTypeStr : main.getFileHandler().getGroupsFile().getData()
                    .getOrDefault("mob-groups." + mobGroupName, new ArrayList<String>())
            ) {
                EntityType entityType;
                try {
                    entityType = EntityType.valueOf(entityTypeStr.toUpperCase(Locale.ROOT));
                } catch(IllegalArgumentException ex) {
                    Utils.LOGGER.error("Invalid entity type '&b" + entityTypeStr + "&7' in the mob " +
                            "group '&b" + mobGroupName + "&7'! Please fix this ASAP.");
                    continue;
                }

                if(entityTypes.contains(entityType)) {
                    Utils.LOGGER.error("Entity type '&b" + entityTypeStr + "&7' has been listed " +
                            "listed more than once in the mob group '&b" + mobGroupName + "&7'! " +
                            "Please fix this ASAP.");
                    continue;
                }

                entityTypes.add(entityType);
            }

            getMobGroups().add(new Group<>(
                    mobGroupName,
                    entityTypes
            ));
        }
    }

    void addBiomeRuleGroups() {
        //TODO
    }

    void addRulePresets() {
        //TODO
    }

    void addRuleListeners() {
        //TODO
    }

    //TODO

}
