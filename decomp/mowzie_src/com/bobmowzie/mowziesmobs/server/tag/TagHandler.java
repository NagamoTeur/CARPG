package com.bobmowzie.mowziesmobs.server.tag;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public class TagHandler {
   public static final TagKey<Item> CAN_HIT_GROTTOL = TagKey.m_203882_(Registry.f_122904_, new ResourceLocation("mowziesmobs", "can_hit_grottol"));
   public static final TagKey<EntityType<?>> UMVUTHANA = TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("mowziesmobs", "umvuthana"));
   public static final TagKey<EntityType<?>> UMVUTHANA_UMVUTHI_ALIGNED = TagKey.m_203882_(
      Registry.f_122903_, new ResourceLocation("mowziesmobs", "umvuthana_umvuthi_aligned")
   );
   public static final TagKey<Biome> HAS_MOWZIE_STRUCTURE = TagKey.m_203882_(
      Registry.f_122885_, new ResourceLocation("mowziesmobs", "has_structure/has_mowzie_structure")
   );
   public static final TagKey<Biome> IS_MAGICAL = TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("mowziesmobs", "is_magical"));
}
