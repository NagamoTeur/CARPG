package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.block.tile.RedstoneRelayTile;
import com.hollingsworth.arsnouveau.common.items.AnimBlockItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.RedStoneWireBlock;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;

public class RedstoneRelayRenderer extends ArsGeoBlockRenderer<RedstoneRelayTile> {
   public static GenericModel model = new GenericModel("redstone_relay");

   public RedstoneRelayRenderer(Context rendererDispatcherIn) {
      super(rendererDispatcherIn, model);
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      ArrayList<String> strings = new ArrayList<>(List.of("framework_input", "bone", "bone2", "bone3", "bone4"));
      if (strings.contains(bone.getName())) {
         super.renderRecursively(
            bone,
            poseStack,
            buffer,
            packedLight,
            packedOverlay,
            (float)Color.WHITE.getRed() / 255.0F,
            (float)Color.WHITE.getGreen() / 255.0F,
            (float)Color.WHITE.getBlue() / 255.0F,
            (float)Color.WHITE.getAlpha() / 255.0F
         );
      } else {
         super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      }
   }

   public Color getRenderColor(
      RedstoneRelayTile animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight
   ) {
      return Color.ofOpaque(RedStoneWireBlock.m_55606_(Math.max(1, animatable.getOutputPower())));
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(model) {
         @Override
         public void renderRecursively(
            GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
         ) {
            super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            ArrayList<String> strings = new ArrayList<>(List.of("framework_input", "bone", "bone2", "bone3", "bone4"));
            if (strings.contains(bone.getName())) {
               super.renderRecursively(
                  bone,
                  poseStack,
                  buffer,
                  packedLight,
                  packedOverlay,
                  (float)Color.WHITE.getRed() / 255.0F,
                  (float)Color.WHITE.getGreen() / 255.0F,
                  (float)Color.WHITE.getBlue() / 255.0F,
                  (float)Color.WHITE.getAlpha() / 255.0F
               );
            } else {
               super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            }
         }

         public Color getRenderColor(
            AnimBlockItem animatable,
            float partialTick,
            PoseStack poseStack,
            @Nullable MultiBufferSource bufferSource,
            @Nullable VertexConsumer buffer,
            int packedLight
         ) {
            return Color.ofOpaque(RedStoneWireBlock.m_55606_(1));
         }
      };
   }
}
