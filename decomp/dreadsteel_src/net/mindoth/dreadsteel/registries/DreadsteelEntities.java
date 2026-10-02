package net.mindoth.dreadsteel.registries;

import net.mindoth.dreadsteel.entity.EntityScytheProjectileBlack;
import net.mindoth.dreadsteel.entity.EntityScytheProjectileBronze;
import net.mindoth.dreadsteel.entity.EntityScytheProjectileDefault;
import net.mindoth.dreadsteel.entity.EntityScytheProjectileWhite;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DreadsteelEntities {
   public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "dreadsteel");
   public static final RegistryObject<EntityType<EntityScytheProjectileDefault>> SCYTHE_PROJECTILE_DEFAULT = registerEntity(
      Builder.m_20704_(EntityScytheProjectileDefault::new, MobCategory.MISC).m_20699_(0.75F, 1.0F).setCustomClientFactory(EntityScytheProjectileDefault::new),
      "scythe_projectile_default"
   );
   public static final RegistryObject<EntityType<EntityScytheProjectileBlack>> SCYTHE_PROJECTILE_BLACK = registerEntity(
      Builder.m_20704_(EntityScytheProjectileBlack::new, MobCategory.MISC).m_20699_(0.75F, 1.0F).setCustomClientFactory(EntityScytheProjectileBlack::new),
      "scythe_projectile_black"
   );
   public static final RegistryObject<EntityType<EntityScytheProjectileBronze>> SCYTHE_PROJECTILE_BRONZE = registerEntity(
      Builder.m_20704_(EntityScytheProjectileBronze::new, MobCategory.MISC).m_20699_(0.75F, 1.0F).setCustomClientFactory(EntityScytheProjectileBronze::new),
      "scythe_projectile_bronze"
   );
   public static final RegistryObject<EntityType<EntityScytheProjectileWhite>> SCYTHE_PROJECTILE_WHITE = registerEntity(
      Builder.m_20704_(EntityScytheProjectileWhite::new, MobCategory.MISC).m_20699_(0.75F, 1.0F).setCustomClientFactory(EntityScytheProjectileWhite::new),
      "scythe_projectile_white"
   );

   private static final <T extends Entity> RegistryObject<EntityType<T>> registerEntity(Builder<T> builder, String entityName) {
      return ENTITIES.register(entityName, () -> builder.m_20712_(entityName));
   }
}
