package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Blazing_Bone_Entity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Blazing_Bone_Renderer extends EntityRenderer<Blazing_Bone_Entity> {
   public Blazing_Bone_Renderer(Context manager) {
      super(manager);
   }

   public void render(Blazing_Bone_Entity entity, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
      stack.m_85836_();
      float spin = ((float)entity.f_19797_ + partialTicks) * 30.0F;
      stack.m_85841_(1.25F, 1.25F, 1.25F);
      stack.m_85836_();
      stack.m_85845_(Vector3f.f_122225_.m_122240_(yaw + 90.0F));
      stack.m_85845_(Vector3f.f_122227_.m_122240_(spin));
      stack.m_85837_(0.0, 0.0, 0.0);
      Minecraft.m_91087_().m_91291_().m_174269_(entity.m_7846_(), TransformType.GROUND, light, OverlayTexture.f_118083_, stack, buffer, entity.m_19879_());
      stack.m_85849_();
      stack.m_85849_();
   }

   public ResourceLocation getTextureLocation(Blazing_Bone_Entity entity) {
      return TextureAtlas.f_118259_;
   }
}
