package com.rolfmao.upgradednetherite.client;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldTextures {
   public static final Material LOCATION_NETHERITE_SHIELD_BASE = material("entity/netherite_shield_base");
   public static final Material LOCATION_GOLD_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/gold_upgraded_netherite_shield_base");
   public static final Material LOCATION_FIRE_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/fire_upgraded_netherite_shield_base");
   public static final Material LOCATION_ENDER_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/ender_upgraded_netherite_shield_base");
   public static final Material LOCATION_WATER_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/water_upgraded_netherite_shield_base");
   public static final Material LOCATION_WITHER_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/wither_upgraded_netherite_shield_base");
   public static final Material LOCATION_POISON_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/poison_upgraded_netherite_shield_base");
   public static final Material LOCATION_PHANTOM_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/phantom_upgraded_netherite_shield_base");
   public static final Material LOCATION_FEATHER_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/feather_upgraded_netherite_shield_base");
   public static final Material LOCATION_CORRUPT_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/corrupt_upgraded_netherite_shield_base");
   public static final Material LOCATION_ECHO_UPGRADED_NETHERITE_SHIELD_BASE = material("entity/echo_upgraded_netherite_shield_base");

   private static Material material(String path) {
      return new Material(TextureAtlas.f_118259_, new ResourceLocation("upgradednetherite", path));
   }
}
