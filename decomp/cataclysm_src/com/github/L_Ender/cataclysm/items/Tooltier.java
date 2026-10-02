package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.List;
import java.util.Locale;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

public class Tooltier {
   public static final Tier ANCIENT_METAL = TierSortingRegistry.registerTier(
      new ForgeTier(
         3,
         750,
         8.0F,
         2.0F,
         25,
         BlockTags.create(prefix("needs_ancient_metal_tool")),
         () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ModItems.ANCIENT_METAL_INGOT.get()})
      ),
      prefix("ancient_metal"),
      List.of(Tiers.IRON),
      List.of(Tiers.DIAMOND)
   );
   public static final Tier BLACK_STEEL = TierSortingRegistry.registerTier(
      new ForgeTier(
         3,
         750,
         8.0F,
         2.0F,
         25,
         BlockTags.create(prefix("needs_black_steel_tool")),
         () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ModItems.BLACK_STEEL_INGOT.get()})
      ),
      prefix("black_steel"),
      List.of(Tiers.IRON),
      List.of(Tiers.DIAMOND)
   );
   public static final Tier MONSTROSITY = TierSortingRegistry.registerTier(
      new ForgeTier(
         4,
         2800,
         9.0F,
         4.0F,
         25,
         BlockTags.create(prefix("needs_monstrosity_tool")),
         () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ModItems.MONSTROUS_HORN.get()})
      ),
      prefix("monstrosity"),
      List.of(Tiers.NETHERITE),
      List.of()
   );

   private static ResourceLocation prefix(String name) {
      return new ResourceLocation("cataclysm", name.toLowerCase(Locale.ROOT));
   }
}
