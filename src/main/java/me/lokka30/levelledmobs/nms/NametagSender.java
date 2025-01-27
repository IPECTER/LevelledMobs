package me.lokka30.levelledmobs.nms;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import me.lokka30.levelledmobs.LevelledMobs;
import me.lokka30.levelledmobs.result.NametagResult;
import me.lokka30.microlib.messaging.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sends NMS verison specific nametag packets to players
 *
 * @author stumper66
 * @since 3.6.0
 */
@SuppressWarnings("unchecked")
public class NametagSender implements NMSUtil {

    public NametagSender() {
        this.def = LevelledMobs.getInstance().getDefinitions();
    }

    private final Definitions def;

    public void sendNametag(final @NotNull LivingEntity livingEntity, final @NotNull NametagResult nametag,
                            final @NotNull Player player, final boolean doAlwaysVisible) {

        if (!player.isOnline() || !player.isValid()) return;

        final Runnable runnable = () -> sendNametagNonAsync(livingEntity, nametag, player, doAlwaysVisible);

        Bukkit.getScheduler().runTask(LevelledMobs.getInstance(), runnable);
    }

    private void sendNametagNonAsync(final @NotNull LivingEntity livingEntity, final @NotNull NametagResult nametag,
                            final @NotNull Player player, final boolean doAlwaysVisible) {

        try {
            // livingEntity.getHandle()
            final Object internalLivingEntity = def.method_getHandle.invoke(livingEntity);
            // internalLivingEntity.getEntityData()
            final Object entityDataPreClone = def.method_getEntityData.invoke(internalLivingEntity);
            final Object entityData = cloneEntityData(entityDataPreClone, internalLivingEntity);

            if (entityData == null){
                return;
            }

            //final Object entityData = entityDataPreClone;
            final Object optionalComponent = def.field_OPTIONAL_COMPONENT.get(def.clazz_DataWatcherRegistry);

            // final EntityDataAccessor<Optional<Component>> customNameAccessor =
            //     //new EntityDataAccessor<>(2, EntityDataSerializers.OPTIONAL_COMPONENT);
            final Object customNameAccessor = def.ctor_EntityDataAccessor.newInstance(2, optionalComponent);
            final Optional<Object> customName = buildNametagComponent(livingEntity, nametag);

            //final Optional<Object> customName = entityData.set(customNameAccessor, customName);
            def.method_set.invoke(entityData, customNameAccessor, customName);

            final Object BOOLEAN = def.field_BOOLEAN.get(def.clazz_DataWatcherRegistry);
            final Object customNameVisibleAccessor = def.ctor_EntityDataAccessor.newInstance(3, BOOLEAN);

            // entityData.set(customNameVisibleAccessor, !nametag.isNullOrEmpty() && doAlwaysVisible);
            def.method_set.invoke(entityData, customNameVisibleAccessor, doAlwaysVisible);

            final int livingEntityId = (int)def.method_getId.invoke(internalLivingEntity);

            Object packet;
            if (def.getIsOneNinteenThreeOrNewer()){
                // List<DataWatcher.b<?>>
                // java.util.List getAllNonDefaultValues() -> c
                final List<?> getAllNonDefaultValues = getNametagFields(entityData);
                packet = def.ctor_Packet
                        .newInstance(livingEntityId, getAllNonDefaultValues);
            }
            else{
                packet = def.ctor_Packet
                        .newInstance(livingEntityId, entityData, true);
            }

            final Object serverPlayer = def.method_PlayergetHandle.invoke(player);
            final Object connection = def.field_Connection.get(serverPlayer);

            // serverPlayer.connection.send(packet);
            def.method_Send.invoke(connection, packet);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    // returns SynchedEntityData (DataWatcher)
    // args: SynchedEntityData, LivingEntity (nms)
    private @Nullable Object cloneEntityData(
            final @NotNull Object entityDataPreClone,
            final @NotNull Object internalLivingEntity
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException {

        if (!def.getIsOneNinteenThreeOrNewer()) {
            return cloneEntityDataLegacy(entityDataPreClone, internalLivingEntity);
        }

        // constructor:
        // public net.minecraft.network.syncher.DataWatcher(net.minecraft.world.entity.Entity)
        final Object entityData = def.ctor_SynchedEntityData.newInstance(internalLivingEntity);

        try{
            final Map<Integer, Object> itemsById = (Map<Integer, Object>)
                    def.field_Int2ObjectMap.get(entityDataPreClone);
            if (itemsById.isEmpty()) {
                return null;
            }

            for (final Object objDataItem : itemsById.values()){
                final Object accessor = def.method_getAccessor.invoke(objDataItem);
                final Object value = def.method_getValue.invoke(objDataItem);
                def.method_define.invoke(entityData, accessor, value);
            }
            return entityData;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return entityData;
    }

    private @NotNull Object cloneEntityDataLegacy(
            final @NotNull Object entityDataPreClone,
            final @NotNull Object internalLivingEntity
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException {

        final Object entityData = def.ctor_SynchedEntityData.newInstance(internalLivingEntity);
        if (def.method_getAll.invoke(entityDataPreClone) == null){
            return entityData;
        }

        // SynchedEntityData.DataItem
        // List<DataItem<?>> getAll()
        for (final Object dataItem : (List<?>)def.method_getAll.invoke(entityDataPreClone)){
            // entityData.define(dataItem.getAccessor(), dataItem.getValue());
            final Object accessor = def.method_getAccessor.invoke(dataItem);
            final Object value = def.method_getValue.invoke(dataItem);
            def.method_define.invoke(entityData, accessor, value);
        }

        return entityData;
    }

    private @NotNull List<Object> getNametagFields(final @NotNull Object entityData){
        final List<Object> results = new LinkedList<>();
        try{
            final Map<Integer, Object> itemsById = (Map<Integer, Object>)
                    def.field_Int2ObjectMap.get(entityData);
            if (itemsById.isEmpty()) {
                return results;
            }

            for (final int objDataId : itemsById.keySet()){
                if (objDataId < 2 || objDataId > 3) continue;
                final Object objDataItem = itemsById.get(objDataId);
                final Object accessor = def.method_getAccessor.invoke(objDataItem);

                // DataWatcher.Item
                final Object dataWatcherItem = def.method_DataWatcher_GetItem.invoke(entityData, accessor);
                results.add(def.method_DataWatcherItem_Value.invoke(dataWatcherItem));
                //results.add(objDataItem);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return results;
    }

    private Optional<Object> buildNametagComponent(
            final @NotNull LivingEntity livingEntity,
            final @NotNull NametagResult nametag
    ) {
        if (nametag.isNullOrEmpty()) {
            return Optional.of(ComponentUtils.getEmptyComponent());
        }

        if (def.getHasKiori()){
            // paper servers go here:
            return Optional.of(KyoriNametags.generateComponent(livingEntity, nametag));
        }

        // the rest of this method will only be used on spigot servers

        final String mobName = nametag.getNametagNonNull();
        final String displayName = "{DisplayName}";
        final int displayNameIndex = mobName.indexOf(displayName);

        if (displayNameIndex < 0) {
            final Object comp = ComponentUtils.getTextComponent(nametag.getNametagNonNull());
            return comp == null ?
                    Optional.empty() : Optional.of(comp);
        }

        final String leftText = displayNameIndex > 0 ?
                resolveText(mobName.substring(0, displayNameIndex)) :
                null;
        final String rightText = mobName.length() > displayNameIndex + displayName.length() ?
                resolveText(mobName.substring(displayNameIndex + displayName.length())) :
                null;


        final Object mobNameComponent = nametag.overriddenName == null ?
                ComponentUtils.getTranslatableComponent(def.getTranslationKey(livingEntity)) :
                ComponentUtils.getTextComponent(nametag.overriddenName);

        final Object comp = ComponentUtils.getEmptyComponent();
        // MutableComponent comp = Component.empty();

        if (leftText != null) {
            // comp.append(Component);
            ComponentUtils.append(comp, ComponentUtils.getTextComponent(leftText));
        }

        ComponentUtils.append(comp, mobNameComponent);

        if (rightText != null) {
            // comp.append(Component);
            ComponentUtils.append(comp, ComponentUtils.getTextComponent(rightText));
        }

        return Optional.of(comp);
    }

    private @Nullable String resolveText(final @Nullable String text){
        if (text == null || text.isEmpty()) return null;

        String result = text;
        if (text.contains("&#"))
            result = MessageUtils.colorizeHexCodes(text);
        if (text.contains("&"))
            result = MessageUtils.colorizeAll(result);

        return result;
    }

    public String toString() {
        return "Nametags_NMS";
    }
}
