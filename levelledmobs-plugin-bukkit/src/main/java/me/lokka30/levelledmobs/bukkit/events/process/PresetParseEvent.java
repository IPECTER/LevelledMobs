package me.lokka30.levelledmobs.bukkit.events.process;

import me.lokka30.levelledmobs.bukkit.logic.LmFunction;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class PresetParseEvent extends Event implements Cancellable {

    /* vars */

    private static final HandlerList HANDLERS = new HandlerList();

    private final LmFunction function;
    private boolean isCancelled = false;

    /* constructors */

    public PresetParseEvent(
        final @NotNull LmFunction function
    ) {
        this.function = function;
    }

    /* getters and setters */

    @NotNull
    public LmFunction getFunction() { return function; }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.isCancelled = state;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
