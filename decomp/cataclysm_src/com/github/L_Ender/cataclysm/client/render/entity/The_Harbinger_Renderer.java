package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.The_Harbinger_Model;
import com.github.L_Ender.cataclysm.client.render.layer.The_Harbinger_Item_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.The_Harbinger_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.The_Harbinger_Shield_Layer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class The_Harbinger_Renderer extends MobRenderer<The_Harbinger_Entity, The_Harbinger_Model> {
   private static final ResourceLocation HARBINGER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/harbinger/the_harbinger.png");
   private final RandomSource rnd = RandomSource.m_216327_();
   private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0) / 2.0);

   public The_Harbinger_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new The_Harbinger_Model(), 1.0F);
      this.m_115326_(new The_Harbinger_Layer(this));
      this.m_115326_(new The_Harbinger_Shield_Layer(this));
      this.m_115326_(new The_Harbinger_Item_Layer(this, ((The_Harbinger_Model)this.m_7200_()).nether_star, Items.f_42686_.m_7968_(), TransformType.GROUND));
   }

   public ResourceLocation getTextureLocation(The_Harbinger_Entity entity) {
      return HARBINGER_TEXTURES;
   }

   public Vec3 getRenderOffset(The_Harbinger_Entity entityIn, float partialTicks) {
      if ((entityIn.getAnimation() != The_Harbinger_Entity.DEATHLASER_ANIMATION || entityIn.getAnimationTick() < 27 || entityIn.getAnimationTick() > 48)
         && entityIn.getAnimation() != The_Harbinger_Entity.DEATH_ANIMATION
         && (entityIn.getAnimation() != The_Harbinger_Entity.STUN_ANIAMATION || entityIn.getAnimationTick() > 90)) {
         return super.m_7860_(entityIn, partialTicks);
      } else {
         double d0 = 0.05;
         return new Vec3(this.rnd.m_188583_() * d0, 0.0, this.rnd.m_188583_() * d0);
      }
   }

   public void render(The_Harbinger_Entity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      if (entity.f_20919_ > 0) {
         float f5 = ((float)entity.f_20919_ + partialTicks) / 144.0F;
         float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
         RandomSource randomsource = RandomSource.m_216335_(432L);
         VertexConsumer vertexconsumer2 = bufferIn.m_6299_(RenderType.m_110502_());
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.0, 1.8, 0.0);

         for (int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 30.0F; i++) {
            matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(randomsource.m_188501_() * 360.0F));
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(randomsource.m_188501_() * 360.0F));
            matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(randomsource.m_188501_() * 360.0F));
            matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(randomsource.m_188501_() * 360.0F));
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(randomsource.m_188501_() * 360.0F));
            matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(randomsource.m_188501_() * 360.0F + f5 * 90.0F));
            float f3 = randomsource.m_188501_() * 5.0F + 5.0F + f7 * 10.0F;
            float f4 = randomsource.m_188501_() * 0.5F + 1.0F + f7 * 2.0F;
            Matrix4f matrix4f = matrixStackIn.m_85850_().m_85861_();
            int j = (int)(255.0F * (1.0F - f7));
            vertex01(vertexconsumer2, matrix4f, j);
            vertex2(vertexconsumer2, matrix4f, f3, f4);
            vertex3(vertexconsumer2, matrix4f, f3, f4);
            vertex01(vertexconsumer2, matrix4f, j);
            vertex3(vertexconsumer2, matrix4f, f3, f4);
            vertex4(vertexconsumer2, matrix4f, f3, f4);
            vertex01(vertexconsumer2, matrix4f, j);
            vertex4(vertexconsumer2, matrix4f, f3, f4);
            vertex2(vertexconsumer2, matrix4f, f3, f4);
         }

         matrixStackIn.m_85849_();
      }

      matrixStackIn.m_85849_();
      super.m_7392_(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private static void vertex01(VertexConsumer p_114220_, Matrix4f p_114221_, int p_114222_) {
      p_114220_.m_85982_(p_114221_, 0.0F, 0.0F, 0.0F).m_6122_(255, 51, 0, p_114222_).m_5752_();
   }

   private static void vertex2(VertexConsumer p_114215_, Matrix4f p_114216_, float p_114217_, float p_114218_) {
      p_114215_.m_85982_(p_114216_, -HALF_SQRT_3 * p_114218_, p_114217_, -0.5F * p_114218_).m_6122_(255, 51, 0, 0).m_5752_();
   }

   private static void vertex3(VertexConsumer p_114224_, Matrix4f p_114225_, float p_114226_, float p_114227_) {
      p_114224_.m_85982_(p_114225_, HALF_SQRT_3 * p_114227_, p_114226_, -0.5F * p_114227_).m_6122_(255, 51, 0, 0).m_5752_();
   }

   private static void vertex4(VertexConsumer p_114229_, Matrix4f p_114230_, float p_114231_, float p_114232_) {
      p_114229_.m_85982_(p_114230_, 0.0F, p_114231_, 1.0F * p_114232_).m_6122_(255, 51, 0, 0).m_5752_();
   }

   protected void scale(The_Harbinger_Entity entityIn, PoseStack p_116440_, float p_116441_) {
      float f = 2.0F;
      p_116440_.m_85841_(f, f, f);
   }

   protected int getBlockLightLevel(The_Harbinger_Entity entityIn, BlockPos pos) {
      return 15;
   }

   protected float getFlipDegrees(The_Harbinger_Entity entity) {
      return 0.0F;
   }
}
