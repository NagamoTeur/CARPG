package com.hollingsworth.arsnouveau.common.light;

import com.hollingsworth.arsnouveau.setup.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class DynamLightUtil {
   public static int getSectionCoord(double coord) {
      return getSectionCoord(Mth.m_14107_(coord));
   }

   public static int getSectionCoord(int coord) {
      return coord >> 4;
   }

   private static int getLuminance(Entity entity) {
      if (!entity.m_6060_() && !entity.m_142038_()) {
         return Config.ENTITY_LIGHT_MAP.containsKey(keyFor(entity)) ? Config.ENTITY_LIGHT_MAP.get(keyFor(entity)) : Math.min(15, LightManager.getValue(entity));
      } else {
         return 15;
      }
   }

   public static boolean couldGiveLight(Entity entity) {
      return LightManager.getLightRegistry().containsKey(entity.m_6095_())
         || Config.ENTITY_LIGHT_MAP.containsKey(keyFor(entity))
         || entity instanceof Player player && getPlayerLight(player) > 0
         || entity.m_6060_()
         || entity.m_142038_();
   }

   public static int getPlayerLight(Player player) {
      int max = 0;

      for (ItemStack item : player.m_20158_()) {
         if (!item.m_41619_()) {
            max = Math.max(max, Config.ITEM_LIGHTMAP.getOrDefault(keyFor(item.m_41720_()), 0));
         }
      }

      return max;
   }

   public static int lightForEntity(Entity entity) {
      int light = 0;
      if (entity instanceof Player player) {
         light = getPlayerLight(player);
      }

      if (entity.m_6060_() || entity.m_142038_()) {
         return 15;
      } else if (light < 15 && LightManager.containsEntity(entity.m_6095_())) {
         int entityLuminance = getLuminance(entity);
         return Math.max(entityLuminance, light);
      } else {
         return Math.min(15, light);
      }
   }

   public static int fromItemLike(ItemLike itemLike) {
      return Config.ITEM_LIGHTMAP.getOrDefault(keyFor(itemLike), 0);
   }

   public static ResourceLocation keyFor(Entity entity) {
      return ForgeRegistries.ENTITY_TYPES.getKey(entity.m_6095_());
   }

   public static ResourceLocation keyFor(ItemLike itemLike) {
      return ForgeRegistries.ITEMS.getKey(itemLike.m_5456_());
   }
}
