package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.etc.LightningBoltData;
import com.github.L_Ender.cataclysm.client.render.etc.LightningRender;
import com.github.L_Ender.cataclysm.entity.effect.Boltstrike_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector4f;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Boltstrike_Renderer extends EntityRenderer<Boltstrike_Entity> {
   private Map<UUID, LightningRender> lightningRenderMap = new HashMap<>();
   private static final int MAX_HEIGHT = 15;
   private static final double START_MIN_RADIUS = 0.7;
   private static final double START_MAX_RADIUS = 1.2;
   private static final double END_MIN_RADIUS = 0.1;
   private static final double END_MAX_RADIUS = 0.5;

   public Boltstrike_Renderer(Context p_174286_) {
      super(p_174286_);
   }

   public void render(Boltstrike_Entity entity, float p_115267_, float partialTicks, PoseStack poseStack, MultiBufferSource p_115270_, int p_115271_) {
      double x = Mth.m_14139_((double)partialTicks, entity.f_19790_, entity.m_20185_());
      double y = Mth.m_14139_((double)partialTicks, entity.f_19791_, entity.m_20186_());
      double z = Mth.m_14139_((double)partialTicks, entity.f_19792_, entity.m_20189_());
      int r = entity.getR();
      int g = entity.getG();
      int b = entity.getB();
      float f = entity.getAnimationProgress(partialTicks);
      float f1 = 0.0F;
      if (f != 0.0F) {
         poseStack.m_85836_();
         poseStack.m_85837_(-x, -y, -z);
         LightningBoltData.BoltRenderInfo boltData = new LightningBoltData.BoltRenderInfo(
            0.2F, 0.7F, 0.25F, 0.15F, new Vector4f((float)r / 255.0F, (float)g / 255.0F, (float)b / 255.0F, 0.7F), 0.92F
         );
         LightningBoltData bolt1 = new LightningBoltData(
               boltData, entity.getAnglePosition(partialTicks, 15.0, 1.2, 0.7), entity.getAnglePosition(partialTicks, 0.0, 0.5, 0.1), 4
            )
            .size(f)
            .lifespan(1)
            .spawn(LightningBoltData.SpawnFunction.NO_DELAY)
            .fade(LightningBoltData.FadeFunction.NONE);
         LightningRender lightningRender = this.getLightingRender(entity.m_20148_());
         if (!Minecraft.m_91087_().m_91104_()) {
            lightningRender.update(entity, bolt1, partialTicks);
         }

         lightningRender.render(partialTicks, poseStack, p_115270_);
         poseStack.m_85849_();
      }

      if (entity.m_213877_() && this.lightningRenderMap.containsKey(entity.m_20148_())) {
         this.lightningRenderMap.remove(entity.m_20148_());
      }
   }

   private LightningRender getLightingRender(UUID uuid) {
      if (this.lightningRenderMap.get(uuid) == null) {
         this.lightningRenderMap.put(uuid, new LightningRender());
      }

      return this.lightningRenderMap.get(uuid);
   }

   public ResourceLocation getTextureLocation(Boltstrike_Entity p_115264_) {
      return TextureAtlas.f_118259_;
   }
}
