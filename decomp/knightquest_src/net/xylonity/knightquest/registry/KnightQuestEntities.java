package net.xylonity.knightquest.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.xylonity.knightquest.common.entity.boss.NethermanCloneEntity;
import net.xylonity.knightquest.common.entity.boss.NethermanEntity;
import net.xylonity.knightquest.common.entity.boss.NethermanProjectileChargeEntity;
import net.xylonity.knightquest.common.entity.entities.BadPatchEntity;
import net.xylonity.knightquest.common.entity.entities.EldBombEntity;
import net.xylonity.knightquest.common.entity.entities.EldKnightEntity;
import net.xylonity.knightquest.common.entity.entities.GhastlingEntity;
import net.xylonity.knightquest.common.entity.entities.GhostyEntity;
import net.xylonity.knightquest.common.entity.entities.GremlinEntity;
import net.xylonity.knightquest.common.entity.entities.LizzyEntity;
import net.xylonity.knightquest.common.entity.entities.RatmanEntity;
import net.xylonity.knightquest.common.entity.entities.SamhainEntity;
import net.xylonity.knightquest.common.entity.entities.SwampmanAxeEntity;
import net.xylonity.knightquest.common.entity.entities.SwampmanEntity;

public class KnightQuestEntities {
   public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "knightquest");
   public static final RegistryObject<EntityType<GremlinEntity>> GREMLIN = register("gremlin", GremlinEntity::new, MobCategory.MONSTER, 1.0F, 1.0F);
   public static final RegistryObject<EntityType<EldBombEntity>> ELDBOMB = register("eldbomb", EldBombEntity::new, MobCategory.MONSTER, 1.0F, 1.0F);
   public static final RegistryObject<EntityType<EldKnightEntity>> ELDKINGHT = register("eldknight", EldKnightEntity::new, MobCategory.MONSTER, 1.0F, 2.6F);
   public static final RegistryObject<EntityType<SwampmanEntity>> SWAMPMAN = register("swampman", SwampmanEntity::new, MobCategory.MONSTER, 1.0F, 2.0F);
   public static final RegistryObject<EntityType<SamhainEntity>> SAMHAIN = register("samhain", SamhainEntity::new, MobCategory.MONSTER, 1.0F, 1.5F);
   public static final RegistryObject<EntityType<RatmanEntity>> RATMAN = register("ratman", RatmanEntity::new, MobCategory.MONSTER, 1.0F, 1.0F);
   public static final RegistryObject<EntityType<LizzyEntity>> LIZZY = register("lizzy", LizzyEntity::new, MobCategory.AMBIENT, 1.0F, 0.3F);
   public static final RegistryObject<EntityType<BadPatchEntity>> BADPATCH = register("bad_patch", BadPatchEntity::new, MobCategory.MONSTER, 1.0F, 1.0F);
   public static final RegistryObject<EntityType<GhastlingEntity>> SHIELD = register("ghastling", GhastlingEntity::new, MobCategory.MONSTER, 0.65F, 0.65F);
   public static final RegistryObject<EntityType<GhostyEntity>> GHOSTY = register("ghosty", GhostyEntity::new, MobCategory.MONSTER, 1.0F, 1.0F);
   public static final RegistryObject<EntityType<NethermanEntity>> NETHERMAN = register("netherman", NethermanEntity::new, MobCategory.MONSTER, 0.8F, 2.8F);
   public static final RegistryObject<EntityType<NethermanCloneEntity>> NETHERMAN_CLONE = register(
      "netherman_clone", NethermanCloneEntity::new, MobCategory.MONSTER, 0.8F, 2.8F
   );
   public static final RegistryObject<EntityType<NethermanProjectileChargeEntity>> NETHERMAN_PROJECTILE_CHARGE = register(
      "netherman_projectile_charge", NethermanProjectileChargeEntity::new, MobCategory.MISC, 0.5F, 0.5F
   );
   public static final RegistryObject<EntityType<SwampmanAxeEntity>> SWAMPMAN_AXE = register(
      "swampman_axe", SwampmanAxeEntity::new, MobCategory.MISC, 0.3F, 1.0F
   );

   private static <X extends Entity> RegistryObject<EntityType<X>> register(
      String name, EntityFactory<X> entity, MobCategory category, float width, float height
   ) {
      return ENTITY.register(
         name, () -> Builder.m_20704_(entity, category).m_20699_(width, height).m_20712_(String.valueOf(new ResourceLocation("knightquest", name)))
      );
   }
}
