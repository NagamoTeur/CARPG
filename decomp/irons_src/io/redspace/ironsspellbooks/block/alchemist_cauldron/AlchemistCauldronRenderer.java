package io.redspace.ironsspellbooks.block.alchemist_cauldron;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class AlchemistCauldronRenderer implements BlockEntityRenderer<AlchemistCauldronTile> {
   ItemRenderer itemRenderer;
   private static final Vec3 ITEM_POS = new Vec3(0.5, 1.5, 0.5);

   public AlchemistCauldronRenderer(Context context) {
      this.itemRenderer = context.m_234447_();
   }

   public void render(
      AlchemistCauldronTile cauldron, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay
   ) {
      int waterLevel = cauldron.getLiquidLevel();
      float waterOffset = Mth.m_14179_((float)waterLevel / (float)AlchemistCauldronTile.MAX_LEVELS, 0.25F, 0.9F);
      if (waterLevel > 0) {
         this.renderWater(cauldron, poseStack, bufferSource, packedLight, waterOffset);
      }

      NonNullList<ItemStack> floatingItems = cauldron.inputItems;

      for (int i = 0; i < floatingItems.size(); i++) {
         ItemStack itemStack = (ItemStack)floatingItems.get(i);
         if (!itemStack.m_41619_()) {
            float f = waterLevel > 0 ? (float)cauldron.m_58904_().m_46467_() + partialTick : 15.0F;
            Vec2 floatOffset = this.getFloatingItemOffset(f, i * 587);
            float yRot = (f + (float)(i * 213)) / (float)(i + 1) * 1.5F;
            this.renderItem(
               itemStack,
               new Vec3((double)floatOffset.f_82470_, (double)(waterOffset + (float)i * 0.01F), (double)floatOffset.f_82471_),
               yRot,
               cauldron,
               partialTick,
               poseStack,
               bufferSource,
               packedLight,
               packedOverlay
            );
         }
      }
   }

   public Vec2 getFloatingItemOffset(float time, int offset) {
      float xspeed = offset % 2 == 0 ? 0.0075F : 0.025F * (1.0F + (float)(offset % 88) * 0.001F);
      float yspeed = offset % 2 == 0 ? 0.025F : 0.0075F * (1.0F + (float)(offset % 88) * 0.001F);
      float x = (time + (float)offset) * xspeed;
      x = (Math.abs(x % 2.0F - 1.0F) + 1.0F) / 2.0F;
      float y = (time + (float)offset + 4356.0F) * yspeed;
      y = (Math.abs(y % 2.0F - 1.0F) + 1.0F) / 2.0F;
      x = Mth.m_14179_(x, -0.2F, 0.75F);
      y = Mth.m_14179_(y, -0.2F, 0.75F);
      return new Vec2(x, y);
   }

   private void renderWater(AlchemistCauldronTile cauldron, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float waterOffset) {
      VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110460_(new ResourceLocation("textures/block/water_still.png"), true));
      long color = (long)cauldron.getAverageWaterColor();
      Vector3f rgb = this.colorFromLong(color);
      Matrix4f pose = poseStack.m_85850_().m_85861_();
      int frames = 32;
      float frameSize = 1.0F / (float)frames;
      long frame = cauldron.m_58904_().m_46467_() / 3L % (long)frames;
      float min_u = 0.0F;
      float max_u = 1.0F;
      float min_v = frameSize * (float)frame;
      float max_v = frameSize * (float)(frame + 1L);
      consumer.m_85982_(pose, 1.0F, waterOffset, 0.0F)
         .m_85950_(rgb.m_122239_(), rgb.m_122260_(), rgb.m_122269_(), 1.0F)
         .m_7421_(max_u, min_v)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(packedLight)
         .m_5601_(0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(pose, 0.0F, waterOffset, 0.0F)
         .m_85950_(rgb.m_122239_(), rgb.m_122260_(), rgb.m_122269_(), 1.0F)
         .m_7421_(min_u, min_v)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(packedLight)
         .m_5601_(0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(pose, 0.0F, waterOffset, 1.0F)
         .m_85950_(rgb.m_122239_(), rgb.m_122260_(), rgb.m_122269_(), 1.0F)
         .m_7421_(min_u, max_v)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(packedLight)
         .m_5601_(0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(pose, 1.0F, waterOffset, 1.0F)
         .m_85950_(rgb.m_122239_(), rgb.m_122260_(), rgb.m_122269_(), 1.0F)
         .m_7421_(max_u, max_v)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(packedLight)
         .m_5601_(0.0F, 1.0F, 0.0F)
         .m_5752_();
   }

   private Vector3f colorFromLong(long color) {
      return new Vector3f((float)(color >> 16 & 255L) / 255.0F, (float)(color >> 8 & 255L) / 255.0F, (float)(color & 255L) / 255.0F);
   }

   private void renderItem(
      ItemStack itemStack,
      Vec3 offset,
      float yRot,
      AlchemistCauldronTile tile,
      float partialTick,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      int packedOverlay
   ) {
      poseStack.m_85836_();
      int renderId = (int)tile.m_58899_().m_121878_();
      poseStack.m_85837_(offset.f_82479_, offset.f_82480_, offset.f_82481_);
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(yRot));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
      poseStack.m_85841_(0.4F, 0.4F, 0.4F);
      this.itemRenderer
         .m_174269_(itemStack, TransformType.FIXED, LevelRenderer.m_109541_(tile.m_58904_(), tile.m_58899_()), packedOverlay, poseStack, bufferSource, renderId);
      poseStack.m_85849_();
   }
}
