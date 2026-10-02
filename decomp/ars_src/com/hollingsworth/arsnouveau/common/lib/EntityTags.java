package com.hollingsworth.arsnouveau.common.lib;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EntityTags {
   public static final TagKey<EntityType<?>> DRYGMY_BLACKLIST = TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("ars_nouveau", "drygmy_blacklist"));
   public static final TagKey<EntityType<?>> DISINTEGRATION_WHITELIST = TagKey.m_203882_(
      Registry.f_122903_, new ResourceLocation("ars_nouveau", "disintegration_whitelist")
   );
   public static final TagKey<EntityType<?>> DISINTEGRATION_BLACKLIST = TagKey.m_203882_(
      Registry.f_122903_, new ResourceLocation("ars_nouveau", "disintegration_blacklist")
   );
   public static final TagKey<EntityType<?>> HOSTILE_MOBS = TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("ars_nouveau", "an_hostile"));
   public static final TagKey<EntityType<?>> MAGIC_FIND = TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("ars_nouveau", "magic_find"));
   public static final TagKey<EntityType<?>> SPELL_CAN_HIT = TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("ars_nouveau", "spell_can_hit"));
   public static final TagKey<EntityType<?>> JAR_BLACKLIST = TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("ars_nouveau", "jar_blacklist"));
   public static final TagKey<EntityType<?>> INTERACT_JAR_BLACKLIST = TagKey.m_203882_(
      Registry.f_122903_, new ResourceLocation("ars_nouveau", "interact_jar_blacklist")
   );
   public static final TagKey<EntityType<?>> JAR_WHITELIST = TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("ars_nouveau", "jar_whitelist"));
   public static final TagKey<EntityType<?>> FAMILIAR = TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("ars_nouveau", "familiar"));
   public static final TagKey<EntityType<?>> LINGERING_BLACKLIST = TagKey.m_203882_(
      Registry.f_122903_, new ResourceLocation("ars_nouveau", "lingering_blacklist")
   );
   public static final TagKey<EntityType<?>> BERRY_BLACKLIST = TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("ars_nouveau", "berry_blacklist"));
   public static final TagKey<EntityType<?>> JAR_RELEASE_BLACKLIST = TagKey.m_203882_(
      Registry.f_122903_, new ResourceLocation("ars_nouveau", "jar_release_blacklist")
   );
   public static final TagKey<EntityType<?>> ANIMAL_SUMMON_BLACKLIST = TagKey.m_203882_(
      Registry.f_122903_, new ResourceLocation("ars_nouveau", "animal_summon_blacklist")
   );
}
