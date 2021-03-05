package io.github.lokka30.levelledmobs.customdrops;

import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class CustomDropInstance {
    public CustomDropInstance(final EntityType associatedMob){
        this.associatedMob = associatedMob;
        this.entityGroup = null;
        this.customItems = new ArrayList<>();
    }

    public CustomDropInstance(final CustomDropsUniversalGroups entityGroup){
        this.associatedMob = null;
        this.entityGroup = entityGroup;
        this.customItems = new ArrayList<>();
    }

    final public EntityType associatedMob;
    final public CustomDropsUniversalGroups entityGroup;
    final public List<CustomItemDrop> customItems;
    public boolean overrideStockDrops;
    public boolean utilizesGroupIds;

    public boolean getIsGroup() {
        return this.entityGroup != null;
    }

    public String getMobOrGroupName() {
        if (this.associatedMob != null)
            return this.associatedMob.name();
        else if (this.entityGroup != null)
            return this.entityGroup.name();
        else
            return ""; // this return should never happen
    }

    public String toString() {
        if (this.associatedMob != null) {
            return this.overrideStockDrops ?
                   this.associatedMob.name() + " - override" :
                   this.associatedMob.name();
        } else {
            return this.overrideStockDrops ?
                   this.entityGroup.toString() + " - override" :
                   this.entityGroup.toString();
        }
    }
}