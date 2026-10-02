package net.cisco.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cisco.item.SovereignAscendantItem;
import net.cisco.item.model.SovereignAscendantModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class SovereignAscendantArmorRenderer extends GeoArmorRenderer<SovereignAscendantItem> {
   public SovereignAscendantArmorRenderer() {
      super(new SovereignAscendantModel());
      this.headBone = "armorHead";
      this.bodyBone = "armorBody";
      this.rightArmBone = "armorRightArm";
      this.leftArmBone = "armorLeftArm";
      this.rightLegBone = "armorRightLeg";
      this.leftLegBone = "armorLeftLeg";
      this.rightBootBone = "armorRightBoot";
      this.leftBootBone = "armorLeftBoot";
   }

   public RenderType getRenderType(
      SovereignAscendantItem animatable,
      float partialTick,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      return RenderType.m_110473_(this.getTextureLocation(animatable));
   }
}
