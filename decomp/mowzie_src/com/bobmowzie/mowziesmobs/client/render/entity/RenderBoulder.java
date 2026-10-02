package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelBoulder;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.BlockLayer;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderBoulder extends EntityRenderer<EntityBoulderBase> {
   private static final ResourceLocation TEXTURE_DIRT = new ResourceLocation("textures/blocks/dirt.png");
   private static final ResourceLocation TEXTURE_STONE = new ResourceLocation("textures/blocks/stone.png");
   private static final ResourceLocation TEXTURE_SANDSTONE = new ResourceLocation("textures/blocks/sandstone.png");
   private static final ResourceLocation TEXTURE_CLAY = new ResourceLocation("textures/blocks/clay.png");
   Map<String, ResourceLocation> texMap;
   ModelBoulder model = new ModelBoulder();

   public RenderBoulder(Context mgr) {
      super(mgr);
      this.texMap = new TreeMap<>();
      this.texMap.put(Blocks.f_50069_.m_7705_(), TEXTURE_STONE);
      this.texMap.put(Blocks.f_50493_.m_7705_(), TEXTURE_DIRT);
      this.texMap.put(Blocks.f_50129_.m_7705_(), TEXTURE_CLAY);
      this.texMap.put(Blocks.f_50062_.m_7705_(), TEXTURE_SANDSTONE);
   }

   public ResourceLocation getTextureLocation(EntityBoulderBase entity) {
      if (entity.storedBlock != null) {
         ResourceLocation tex = this.texMap.get(entity.storedBlock.m_60734_().m_7705_());
         if (tex != null) {
            return tex;
         }
      }

      return TEXTURE_DIRT;
   }

   public void render(EntityBoulderBase entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      if (entityIn.active) {
         matrixStackIn.m_85836_();
         this.model.m_6973_(entityIn, 0.0F, 0.0F, (float)entityIn.risingTick + partialTicks, 0.0F, 0.0F);
         BlockRenderDispatcher blockrendererdispatcher = Minecraft.m_91087_().m_91289_();
         AdvancedModelRenderer root;
         if (entityIn.boulderSize == EntityGeomancyBase.GeomancyTier.SMALL) {
            root = this.model.boulder0block1;
         } else if (entityIn.boulderSize == EntityGeomancyBase.GeomancyTier.MEDIUM) {
            root = this.model.boulder1;
         } else if (entityIn.boulderSize == EntityGeomancyBase.GeomancyTier.LARGE) {
            root = this.model.boulder2;
         } else {
            root = this.model.boulder3;
         }

         matrixStackIn.m_85837_(-0.5, 0.5, -0.5);
         BlockLayer.processModelRenderer(
            root, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F, blockrendererdispatcher
         );
         matrixStackIn.m_85849_();
      }
   }
}
