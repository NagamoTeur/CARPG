package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityRockSling;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class ModelRockSling extends AnimatedTickingGeoModel<EntityRockSling> {
   static Map<String, ResourceLocation> texMap;
   private static final ResourceLocation TEXTURE_DIRT = new ResourceLocation("textures/block/dirt.png");
   private static final ResourceLocation TEXTURE_STONE = new ResourceLocation("textures/block/stone.png");
   private static final ResourceLocation TEXTURE_SANDSTONE = new ResourceLocation("textures/block/sandstone.png");
   private static final ResourceLocation TEXTURE_CLAY = new ResourceLocation("textures/block/clay.png");

   public ModelRockSling() {
      texMap = new TreeMap<>();
      texMap.put(Blocks.f_50069_.m_7705_(), TEXTURE_STONE);
      texMap.put(Blocks.f_50493_.m_7705_(), TEXTURE_DIRT);
      texMap.put(Blocks.f_50129_.m_7705_(), TEXTURE_CLAY);
      texMap.put(Blocks.f_50062_.m_7705_(), TEXTURE_SANDSTONE);
   }

   public ResourceLocation getAnimationResource(EntityRockSling entity) {
      return new ResourceLocation("mowziesmobs", "animations/rock_sling.animation.json");
   }

   public ResourceLocation getModelResource(EntityRockSling entity) {
      return new ResourceLocation("mowziesmobs", "geo/rock_sling.geo.json");
   }

   public ResourceLocation getTextureResource(EntityRockSling entity) {
      if (entity.storedBlock != null) {
         ResourceLocation tex = texMap.get(entity.storedBlock.m_60734_().m_7705_());
         if (tex != null) {
            return tex;
         }
      }

      return TEXTURE_DIRT;
   }
}
