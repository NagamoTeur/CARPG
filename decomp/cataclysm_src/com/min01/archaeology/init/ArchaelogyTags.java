package com.min01.archaeology.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ArchaelogyTags {
   public static final TagKey<Block> TRAIL_RUINS_REPLACEABLE = block("trail_ruins_replaceable");
   public static final TagKey<Item> BREAKS_DECORATED_POTS = item("breaks_decorated_pots");
   public static final TagKey<Item> DECORATED_POT_INGREDIENTS = item("decorated_pot_ingredients");
   public static final TagKey<Item> DECORATED_POT_SHERDS = item("decorated_pot_sherds");
   public static final TagKey<EntityType<?>> IMPACT_PROJECTILES = entityType("impact_projectiles");

   private static TagKey<Block> block(String path) {
      return TagKey.m_203882_(Registry.f_122901_, new ResourceLocation(path));
   }

   private static TagKey<Item> item(String path) {
      return TagKey.m_203882_(Registry.f_122904_, new ResourceLocation(path));
   }

   private static TagKey<EntityType<?>> entityType(String path) {
      return TagKey.m_203882_(Registry.f_122903_, new ResourceLocation(path));
   }
}
