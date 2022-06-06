package me.lokka30.levelledmobs.bukkit.logic;

import java.util.HashSet;
import java.util.LinkedHashSet;
import me.lokka30.levelledmobs.bukkit.LevelledMobs;
import me.lokka30.levelledmobs.bukkit.events.groups.GroupPostParseEvent;
import me.lokka30.levelledmobs.bukkit.events.groups.GroupPreParseEvent;
import me.lokka30.levelledmobs.bukkit.utils.Log;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.SerializationException;

public final class LogicHandler {

    /* vars */

    private final HashSet<Group> groups = new HashSet<>();
    private final HashSet<Preset> presets = new HashSet<>();
    private final LinkedHashSet<LmFunction> functions = new LinkedHashSet<>();

    /* methods */

    /**
    Initialisation - parse functions, presets, groups, etc.
     */
    public boolean load() {
        Log.inf("Loading logic system.");
        return parseGroups() && parsePresets() && parseCustomDrops() && parseFunctions();
    }

    /**
     * Checks all available functions if they have any of the given triggers, and any applicable
     * functions will be ran using the given context.
     *
     * @param context the context given for the function to run with
     * @param triggers a list of trigger IDs, which a function requires at least one match to apply
     */
    public void runFunctionsWithTriggers(
        final @NotNull RunContext context,
        final @NotNull String... triggers
    ) {
        for(var function : getFunctions())
            if(function.hasAnyTriggers(triggers))
                function.run(context);
    }

    private boolean parseGroups() {
        Log.inf("Parsing groups.");

        final var groupsMap = LevelledMobs.getInstance()
                .getConfigHandler().getGroupsCfg()
                .getRoot().node("groups")
                .childrenMap();

        // enumerate through groups map
        for(var groupEntry : groupsMap.entrySet()) {

            // let's make sure that the entry's key is a string (identifiers must be strings)
            if(!(groupEntry.getKey() instanceof String)) {
                Log.sev("Found group with an invalid identifier, '" + groupEntry.getKey() +
                    "', group identifiers must be strings (text).", true);
                // TODO make LM automatically fix these after the updater runs for groups.yml
                return false;
            }

            final var group = new Group((String) groupEntry.getKey());

            // let's make sure that all groups have unique identifiers
            if(getGroups().stream().anyMatch(otherGroup ->
                otherGroup.getIdentifier().equalsIgnoreCase(group.getIdentifier()))
            ) {
                Log.war("Found group with a duplicate identifier, '" + group.getIdentifier() +
                    "', group identifiers must be unique.", true);
                continue;
            }

            // let's call the pre-parse event
            final var preParseEvent = new GroupPreParseEvent(group);
            Bukkit.getPluginManager().callEvent(preParseEvent);
            if(preParseEvent.isCancelled()) continue;

            // we've got the green light to parse it now
            if(groupEntry.getValue().isList()) {
                // group has a list key
                try {
                    group.getItems().addAll(groupEntry.getValue().getList(String.class));
                } catch(SerializationException ex) {
                    Log.sev("Unable to parse group '" + group.getIdentifier() + "': it is " +
                        "highly likely that the user has made a syntax error in the " +
                        "'groups.yml' file. A stack trace will be printed below for " +
                        "debugging purposes.", true);
                    ex.printStackTrace();
                    return false;
                }
            } else {
                Log.sev("Unable to parse group '" + group.getIdentifier() + "' as it does " +
                    "not contain a valid list of items.", true);
                return false;
            }

            // let's add the group to the set of groups
            getGroups().add(group);

            // let's call the post-parse event
            Bukkit.getPluginManager().callEvent(new GroupPostParseEvent(group));
        }

        Log.inf("Successfully parsed " + getGroups().size() + " group(s)");
        return true;
    }

    private boolean parsePresets() {
        Log.inf("Parsing presets.");

        //TODO
        Log.inf("Successfully parsed " + getPresets().size() + " preset(s)");
        return true;
    }

    private boolean parseCustomDrops() {
        Log.inf("Parsing custom drops.");

        //TODO
        Log.inf("Successfully parsed " + "?" + " custom drop(s).");
        return true;
    }

    private boolean parseFunctions() {
        Log.inf("Parsing functions.");

        //TODO
        Log.inf("Successfully parsed " + getFunctions().size() + " function(s).");
        return true;
    }

    /* getters and setters */

    @NotNull
    public HashSet<Group> getGroups() { return groups; }

    @NotNull
    public HashSet<Preset> getPresets() { return presets; }

    @NotNull
    public LinkedHashSet<LmFunction> getFunctions() { return functions; }

}
