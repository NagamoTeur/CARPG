package net.cisco.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cisco.item.DescendedHeroItem;
import net.cisco.item.model.DescendedHeroModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class DescendedHeroArmorRenderer extends GeoArmorRenderer<DescendedHeroItem> {
   public DescendedHeroArmorRenderer() {
      super(new DescendedHeroModel());
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
      DescendedHeroItem animatable,
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
