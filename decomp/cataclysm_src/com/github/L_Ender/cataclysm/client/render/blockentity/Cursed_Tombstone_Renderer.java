package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.Cursed_tombstone_Entity;
import com.github.L_Ender.cataclysm.blocks.Cursed_Tombstone_Block;
import com.github.L_Ender.cataclysm.client.model.block.Cursed_Tombstone_Model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class Cursed_Tombstone_Renderer implements BlockEntityRenderer<Cursed_tombstone_Entity> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/block/cursed_tombstone_off.png");
   private static final ResourceLocation TEXTURE2 = new ResourceLocation("cataclysm", "textures/block/cursed_tombstone_on.png");
   private static final Cursed_Tombstone_Model MODEL = new Cursed_Tombstone_Model();
   private final RandomSource rnd = RandomSource.m_216327_();
   private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0) / 2.0);

   public Cursed_Tombstone_Renderer(Context rendererDispatcherIn) {
   }

   public void render(Cursed_tombstone_Entity entity, float delta, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int overlay) {
      poseStack.m_85836_();
      Direction dir = (Direction)entity.m_58900_().m_61143_(Cursed_Tombstone_Block.FACING);
      poseStack.m_85837_(0.5, 1.5, 0.5);
      poseStack.m_85845_(dir.m_122406_());
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
      MODEL.m_7695_(
         poseStack,
         entity.m_58900_().m_61143_(Cursed_Tombstone_Block.POWERED)
            ? buffer.m_6299_(RenderType.m_110458_(TEXTURE2))
            : buffer.m_6299_(RenderType.m_110458_(TEXTURE)),
         packedLight,
         overlay,
         1.0F,
         1.0F,
         1.0F,
         1.0F
      );
      if (entity.tickCount > 0) {
         float f5 = ((float)entity.tickCount + delta) / 63.0F;
         float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
         RandomSource randomsource = RandomSource.m_216335_(432L);
         VertexConsumer vertexconsumer2 = buffer.m_6299_(RenderType.m_110502_());
         poseStack.m_85836_();
         poseStack.m_85837_(0.0, 0.0, 0.0);

         for (int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 30.0F; i++) {
            poseStack.m_85845_(Vector3f.f_122223_.m_122240_(randomsource.m_188501_() * 360.0F));
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(randomsource.m_188501_() * 360.0F));
            poseStack.m_85845_(Vector3f.f_122227_.m_122240_(randomsource.m_188501_() * 360.0F));
            poseStack.m_85845_(Vector3f.f_122223_.m_122240_(randomsource.m_188501_() * 360.0F));
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(randomsource.m_188501_() * 360.0F));
            poseStack.m_85845_(Vector3f.f_122227_.m_122240_(randomsource.m_188501_() * 360.0F + f5 * 90.0F));
            float f3 = randomsource.m_188501_() * 5.0F + 5.0F + f7 * 5.0F;
            float f4 = randomsource.m_188501_() * 0.5F + 1.0F + f7 * 2.0F;
            Matrix4f matrix4f = poseStack.m_85850_().m_85861_();
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

         poseStack.m_85849_();
      }

      poseStack.m_85849_();
   }

   private static void vertex01(VertexConsumer p_114220_, Matrix4f p_114221_, int p_114222_) {
      p_114220_.m_85982_(p_114221_, 0.0F, 0.0F, 0.0F).m_6122_(57, 210, 178, p_114222_).m_5752_();
   }

   private static void vertex2(VertexConsumer p_114215_, Matrix4f p_114216_, float p_114217_, float p_114218_) {
      p_114215_.m_85982_(p_114216_, -HALF_SQRT_3 * p_114218_, p_114217_, -0.5F * p_114218_).m_6122_(57, 210, 178, 0).m_5752_();
   }

   private static void vertex3(VertexConsumer p_114224_, Matrix4f p_114225_, float p_114226_, float p_114227_) {
      p_114224_.m_85982_(p_114225_, HALF_SQRT_3 * p_114227_, p_114226_, -0.5F * p_114227_).m_6122_(57, 210, 178, 0).m_5752_();
   }

   private static void vertex4(VertexConsumer p_114229_, Matrix4f p_114230_, float p_114231_, float p_114232_) {
      p_114229_.m_85982_(p_114230_, 0.0F, p_114231_, 1.0F * p_114232_).m_6122_(57, 210, 178, 0).m_5752_();
   }
}
