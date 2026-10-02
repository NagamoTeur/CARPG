package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Abyss_Mine_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Mine_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Abyss_Mine_Renderer extends EntityRenderer<Abyss_Mine_Entity> {
   private static final ResourceLocation ABYSS_MINE_TEXTURE = new ResourceLocation("cataclysm", "textures/entity/leviathan/abyss_mine.png");
   private static final float SIN_45 = (float)Math.sin(Math.PI / 4);
   public Abyss_Mine_Model model = new Abyss_Mine_Model();

   public Abyss_Mine_Renderer(Context manager) {
      super(manager);
   }

   protected int getBlockLightLevel(Abyss_Mine_Entity entity, BlockPos pos) {
      return 15;
   }

   public void render(Abyss_Mine_Entity p_114162_, float p_114163_, float p_114164_, PoseStack p_114165_, MultiBufferSource p_114166_, int p_114167_) {
      p_114165_.m_85836_();
      float f1 = ((float)p_114162_.time + p_114164_) * 3.0F;
      float activateProgress = p_114162_.prevactivateProgress + (p_114162_.activateProgress - p_114162_.prevactivateProgress) * p_114164_;
      float d = activateProgress * 0.0875F;
      float e = activateProgress * 0.2F;
      VertexConsumer vertexconsumer = p_114166_.m_6299_(CMRenderTypes.getfullBright(ABYSS_MINE_TEXTURE));
      p_114165_.m_85836_();
      p_114165_.m_85841_(e, e, e);
      p_114165_.m_85837_(0.0, -0.5, 0.0);
      int i = OverlayTexture.f_118083_;
      p_114165_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
      p_114165_.m_85837_(0.0, 0.75, 0.0);
      p_114165_.m_85845_(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
      this.model.glass.render(p_114165_, vertexconsumer, p_114167_, i);
      float f2 = 0.875F;
      p_114165_.m_85841_(d, d, d);
      p_114165_.m_85845_(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
      p_114165_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
      this.model.glass2.render(p_114165_, vertexconsumer, p_114167_, i);
      p_114165_.m_85841_(d, d, d);
      p_114165_.m_85845_(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
      p_114165_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
      this.model.root.render(p_114165_, vertexconsumer, p_114167_, i);
      p_114165_.m_85849_();
      p_114165_.m_85849_();
      super.m_7392_(p_114162_, p_114163_, p_114164_, p_114165_, p_114166_, p_114167_);
   }

   public ResourceLocation getTextureLocation(Abyss_Mine_Entity entity) {
      return ABYSS_MINE_TEXTURE;
   }
}
