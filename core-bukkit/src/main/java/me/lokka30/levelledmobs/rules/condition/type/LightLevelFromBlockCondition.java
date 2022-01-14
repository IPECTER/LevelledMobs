package me.lokka30.levelledmobs.rules.condition.type;

import me.lokka30.levelledmobs.rules.condition.RuleCondition;
import me.lokka30.levelledmobs.rules.condition.RuleConditionType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/*
TODO:
    - Add javadoc comment.
 */
public record LightLevelFromBlockCondition(
        int min,
        int max
) implements RuleCondition {

    @Override
    public @NotNull RuleConditionType getType() {
        return RuleConditionType.LIGHT_LEVEL_FROM_BLOCK;
    }

    @Override
    public boolean appliesTo(@NotNull LivingEntity livingEntity) {
        final byte lightLevel = livingEntity.getLocation().getBlock().getLightFromBlocks();
        return lightLevel >= min && lightLevel <= max;
    }

}
