package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Lionfish_Spike_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Lionfish_Spike_Renderer extends EntityRenderer<Lionfish_Spike_Entity> {
   public Lionfish_Spike_Renderer(Context manager) {
      super(manager);
   }

   public void render(Lionfish_Spike_Entity entity, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
      stack.m_85836_();
      stack.m_85836_();
      stack.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entity.f_19859_, entity.m_146908_()) - 90.0F));
      stack.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_())));
      stack.m_85837_(0.0, 0.0, 0.0);
      Minecraft.m_91087_().m_91291_().m_174269_(entity.m_7846_(), TransformType.GROUND, light, OverlayTexture.f_118083_, stack, buffer, entity.m_19879_());
      stack.m_85849_();
      stack.m_85849_();
   }

   public ResourceLocation getTextureLocation(Lionfish_Spike_Entity entity) {
      return TextureAtlas.f_118259_;
   }
}
