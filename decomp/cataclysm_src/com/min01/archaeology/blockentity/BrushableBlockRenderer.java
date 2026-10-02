package com.min01.archaeology.blockentity;

import com.min01.archaeology.block.BrushableBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BrushableBlockRenderer implements BlockEntityRenderer<BrushableBlockEntity> {
   private final ItemRenderer itemRenderer;

   public BrushableBlockRenderer(Context context) {
      this.itemRenderer = context.m_234447_();
   }

   public void render(
      BrushableBlockEntity brushableBlock, float partialTick, @NotNull PoseStack pose, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay
   ) {
      if (brushableBlock.m_58904_() != null) {
         int dusted = (Integer)brushableBlock.m_58900_().m_61143_(BrushableBlock.DUSTED);
         if (dusted > 0) {
            Direction direction = brushableBlock.getHitDirection();
            if (direction != null) {
               ItemStack stack = brushableBlock.getItem();
               if (!stack.m_41619_()) {
                  pose.m_85836_();
                  pose.m_85837_(0.0, 0.5, 0.0);
                  float[] translations = this.translations(direction, dusted);
                  pose.m_85837_((double)translations[0], (double)translations[1], (double)translations[2]);
                  pose.m_85845_(Vector3f.f_122225_.m_122240_(75.0F));
                  boolean flag = direction == Direction.EAST || direction == Direction.WEST;
                  pose.m_85845_(Vector3f.f_122225_.m_122240_((float)((flag ? 90 : 0) + 11)));
                  pose.m_85841_(0.5F, 0.5F, 0.5F);
                  int combinedLight = LevelRenderer.m_109537_(
                     brushableBlock.m_58904_(), brushableBlock.m_58900_(), brushableBlock.m_58899_().m_121945_(direction)
                  );
                  this.itemRenderer
                     .m_174242_(null, stack, TransformType.FIXED, false, pose, buffer, brushableBlock.m_58904_(), combinedLight, OverlayTexture.f_118083_, 0);
                  pose.m_85849_();
               }
            }
         }
      }
   }

   private float[] translations(Direction direction, int dusted) {
      float[] translations = new float[]{0.5F, 0.0F, 0.5F};
      float f = (float)dusted / 10.0F * 0.75F;
      switch (direction) {
         case EAST:
            translations[0] = 0.73F + f;
            break;
         case WEST:
            translations[0] = 0.25F - f;
            break;
         case UP:
            translations[1] = 0.25F + f;
            break;
         case DOWN:
            translations[1] = -0.23F - f;
            break;
         case NORTH:
            translations[2] = 0.25F - f;
            break;
         case SOUTH:
            translations[2] = 0.73F + f;
      }

      return translations;
   }
}
