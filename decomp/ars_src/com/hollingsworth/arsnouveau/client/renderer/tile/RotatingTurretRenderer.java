package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.common.block.tile.RotatingTurretTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class RotatingTurretRenderer extends ArsGeoBlockRenderer<RotatingTurretTile> {
   public static AnimatedGeoModel model = new GenericModel("basic_spell_turret") {
      @Override
      public void setCustomAnimations(Object animatable, int instanceId, AnimationEvent event) {
         if (animatable instanceof RotatingTurretTile tile) {
            IBone master = this.getAnimationProcessor().getBone("spell_turret");
            master.setRotationY((tile.getRotationX() + 90.0F) * (float) (Math.PI / 180.0));
            master.setRotationX(tile.getRotationY() * (float) (Math.PI / 180.0));
         }
      }
   };

   public RotatingTurretRenderer(Context rendererDispatcherIn) {
      super(rendererDispatcherIn, model);
   }

   public void render(RotatingTurretTile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      super.render(tile, partialTick, poseStack, bufferSource, packedLight);
      float rotationX = tile.rotationX;
      float neededRotationX = tile.clientNeededX;
      float rotationY = tile.rotationY;
      float neededRotationY = tile.clientNeededY;
      float step = 0.1F + partialTick;
      if (rotationX != neededRotationX) {
         float diff = neededRotationX - rotationX;
         if (Math.abs(diff) < step) {
            tile.setRotationX(neededRotationX);
         } else {
            tile.setRotationX(rotationX + diff * step);
         }
      }

      if (rotationY != neededRotationY) {
         float diff = neededRotationY - rotationY;
         if (Math.abs(diff) < step) {
            tile.setRotationY(neededRotationY);
         } else {
            tile.setRotationY(rotationY + diff * step);
         }
      }
   }

   @Override
   protected void rotateBlock(Direction facing, PoseStack poseStack) {
   }
}
