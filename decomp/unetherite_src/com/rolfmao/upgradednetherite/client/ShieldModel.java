package com.rolfmao.upgradednetherite.client;

import com.rolfmao.upgradednetherite.init.ModItems;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "upgradednetherite",
   bus = Bus.MOD
)
public class ShieldModel {
   @SubscribeEvent
   public static void init(FMLClientSetupEvent event) {
      event.enqueueWork(
         () -> addShieldPropertyOverrides(
               new ResourceLocation("upgradednetherite", "blocking"),
               (stack, world, entity, seed) -> entity != null && entity.m_6117_() && entity.m_21211_() == stack ? 1.0F : 0.0F,
               (ItemLike)ModItems.NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.FIRE_UPGRADED_NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.WATER_UPGRADED_NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.WITHER_UPGRADED_NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.POISON_UPGRADED_NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.PHANTOM_UPGRADED_NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.FEATHER_UPGRADED_NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_SHIELD.get(),
               (ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_SHIELD.get()
            )
      );
   }

   private static void addShieldPropertyOverrides(ResourceLocation override, ClampedItemPropertyFunction propertyGetter, ItemLike... shields) {
      for (ItemLike shield : shields) {
         ItemProperties.register(shield.m_5456_(), override, propertyGetter);
      }
   }

   @SubscribeEvent
   public static void onStitch(Pre event) {
      if (event.getAtlas().m_118330_().equals(TextureAtlas.f_118259_)) {
         for (Material textures : new Material[]{
            ShieldTextures.LOCATION_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_GOLD_UPGRADED_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_FIRE_UPGRADED_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_ENDER_UPGRADED_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_WATER_UPGRADED_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_WITHER_UPGRADED_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_POISON_UPGRADED_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_PHANTOM_UPGRADED_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_FEATHER_UPGRADED_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_CORRUPT_UPGRADED_NETHERITE_SHIELD_BASE,
            ShieldTextures.LOCATION_ECHO_UPGRADED_NETHERITE_SHIELD_BASE
         }) {
            event.addSprite(textures.m_119203_());
         }
      }
   }
}
