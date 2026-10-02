package com.aqutheseal.celestisynth.common.registry;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.projectile.RainfallArrow;
import com.aqutheseal.celestisynth.common.entity.skill.SkillCastBreezebreakerTornado;
import com.aqutheseal.celestisynth.common.entity.skill.SkillCastCrescentiaRanged;
import com.aqutheseal.celestisynth.common.entity.skill.SkillCastPoltergeistWard;
import com.aqutheseal.celestisynth.common.entity.skill.SkillCastRainfallRain;
import com.aqutheseal.celestisynth.common.entity.tempestboss.TempestBoss;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSEntityTypes {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "celestisynth");
   public static final RegistryObject<EntityType<TempestBoss>> TEMPEST = ENTITY_TYPES.register(
      "tempest",
      () -> Builder.m_20704_(TempestBoss::new, MobCategory.MONSTER).m_20699_(0.7F, 1.95F).m_20702_(8).m_20712_(Celestisynth.prefix("tempest").toString())
   );
   public static final RegistryObject<EntityType<CSEffectEntity>> CS_EFFECT = ENTITY_TYPES.register(
      "cs_effect",
      () -> Builder.m_20704_(CSEffectEntity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20702_(32).m_20712_(Celestisynth.prefix("cs_effect").toString())
   );
   public static final RegistryObject<EntityType<SkillCastCrescentiaRanged>> CRESCENTIA_RANGED = ENTITY_TYPES.register(
      "crescentia_ranged",
      () -> Builder.m_20704_(SkillCastCrescentiaRanged::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(16)
            .m_20712_(Celestisynth.prefix("crescentia_ranged").toString())
   );
   public static final RegistryObject<EntityType<SkillCastBreezebreakerTornado>> BREEZEBREAKER_TORNADO = ENTITY_TYPES.register(
      "breezebreaker_tornado",
      () -> Builder.m_20704_(SkillCastBreezebreakerTornado::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(16)
            .m_20712_(Celestisynth.prefix("breezebreaker_tornado").toString())
   );
   public static final RegistryObject<EntityType<SkillCastPoltergeistWard>> POLTERGEIST_WARD = ENTITY_TYPES.register(
      "poltergeist_ward",
      () -> Builder.m_20704_(SkillCastPoltergeistWard::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(16)
            .m_20712_(Celestisynth.prefix("poltergeist_ward").toString())
   );
   public static final RegistryObject<EntityType<SkillCastRainfallRain>> RAINFALL_RAIN = ENTITY_TYPES.register(
      "rainfall_rain",
      () -> Builder.m_20704_(SkillCastRainfallRain::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(16)
            .m_20712_(Celestisynth.prefix("rainfall_rain").toString())
   );
   public static final RegistryObject<EntityType<RainfallArrow>> RAINFALL_ARROW = ENTITY_TYPES.register(
      "rainfall_arrow",
      () -> Builder.m_20704_(RainfallArrow::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(32)
            .m_20717_(20)
            .m_20712_(Celestisynth.prefix("rainfall_arrow").toString())
   );
}
