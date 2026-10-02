package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.block.BasicSpellTurret;
import com.hollingsworth.arsnouveau.common.block.tile.BasicSpellTurretTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class BasicTurretRenderer extends ArsGeoBlockRenderer<BasicSpellTurretTile> {
   public static AnimatedGeoModel model = new GenericModel("basic_spell_turret");

   public BasicTurretRenderer(Context rendererDispatcherIn) {
      this(rendererDispatcherIn, model);
   }

   public BasicTurretRenderer(Context rendererDispatcherIn, AnimatedGeoModel<BasicSpellTurretTile> modelProvider) {
      super(rendererDispatcherIn, modelProvider);
   }

   public void render(
      GeoModel model,
      BasicSpellTurretTile animatable,
      float partialTicks,
      RenderType type,
      PoseStack matrixStackIn,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      matrixStackIn.m_85836_();
      Direction direction = (Direction)animatable.m_58900_().m_61143_(BasicSpellTurret.FACING);
      if (direction == Direction.UP) {
         matrixStackIn.m_85837_(0.0, -0.5, -0.5);
      } else if (direction == Direction.DOWN) {
         matrixStackIn.m_85837_(0.0, -0.5, 0.5);
      }

      super.render(
         model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha
      );
      matrixStackIn.m_85849_();
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(model);
   }
}
