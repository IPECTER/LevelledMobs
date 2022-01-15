package me.lokka30.levelledmobs.rules.action.type.executable;

import me.lokka30.levelledmobs.rules.action.type.ExecuteAction;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public record UpdateNametagExecutable(
        @NotNull LinkedList<String> args // ignored on this Executable.
) implements ExecuteAction.Executable {

    @Override
    public ExecuteAction.@NotNull ExecutableType type() {
        return ExecuteAction.ExecutableType.UPDATE_NAMETAG;
    }

    @Override
    public void run(@NotNull LivingEntity livingEntity) {
        if(livingEntity instanceof Player) {
            updateNametagsAroundPlayer((Player) livingEntity);
        } else {
            updateNametagForMob(livingEntity);
        }
    }

    void updateNametagsAroundPlayer(final @NotNull Player player) {
        //TODO
    }

    void updateNametagForMob(final @NotNull LivingEntity livingEntity) {
        //TODO
    }
}