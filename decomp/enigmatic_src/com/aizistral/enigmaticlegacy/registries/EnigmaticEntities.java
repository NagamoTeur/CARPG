package com.aizistral.enigmaticlegacy.registries;

import com.aizistral.enigmaticlegacy.entities.EnigmaticPotionEntity;
import com.aizistral.enigmaticlegacy.entities.PermanentItemEntity;
import com.aizistral.enigmaticlegacy.entities.UltimateWitherSkullEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class EnigmaticEntities extends AbstractRegistry<EntityType<?>> {
   private static final EnigmaticEntities INSTANCE = new EnigmaticEntities();
   @ObjectHolder(
      value = "enigmaticlegacy:permanent_item_entity",
      registryName = "entity_type"
   )
   public static final EntityType<PermanentItemEntity> PERMANENT_ITEM_ENTITY = null;
   @ObjectHolder(
      value = "enigmaticlegacy:enigmatic_potion_entity",
      registryName = "entity_type"
   )
   public static final EntityType<EnigmaticPotionEntity> ENIGMATIC_POTION = null;
   @ObjectHolder(
      value = "enigmaticlegacy:ultimate_wither_skull_entity",
      registryName = "entity_type"
   )
   public static final EntityType<UltimateWitherSkullEntity> ULTIMATE_WITHER_SKULL = null;

   private EnigmaticEntities() {
      super(ForgeRegistries.ENTITY_TYPES);
      this.register(
         "permanent_item_entity",
         () -> Builder.m_20704_(PermanentItemEntity::new, MobCategory.MISC)
               .m_20699_(0.25F, 0.25F)
               .setTrackingRange(64)
               .setCustomClientFactory((spawnEntity, world) -> new PermanentItemEntity(PERMANENT_ITEM_ENTITY, world))
               .setUpdateInterval(2)
               .setShouldReceiveVelocityUpdates(true)
               .m_20712_("enigmaticlegacy:permanent_item_entity")
      );
      this.register(
         "enigmatic_potion_entity",
         () -> Builder.m_20704_(EnigmaticPotionEntity::new, MobCategory.MISC)
               .m_20699_(0.25F, 0.25F)
               .setTrackingRange(64)
               .setCustomClientFactory((spawnEntity, world) -> new EnigmaticPotionEntity(ENIGMATIC_POTION, world))
               .setUpdateInterval(10)
               .setShouldReceiveVelocityUpdates(true)
               .m_20712_("enigmaticlegacy:enigmatic_potion_entity")
      );
      this.register(
         "ultimate_wither_skull_entity",
         () -> Builder.m_20704_(UltimateWitherSkullEntity::new, MobCategory.MISC)
               .m_20699_(0.25F, 0.25F)
               .setTrackingRange(64)
               .setCustomClientFactory((spawnEntity, world) -> new UltimateWitherSkullEntity(ULTIMATE_WITHER_SKULL, world))
               .m_20712_("enigmaticlegacy:ultimate_wither_skull_entity")
      );
   }
}
