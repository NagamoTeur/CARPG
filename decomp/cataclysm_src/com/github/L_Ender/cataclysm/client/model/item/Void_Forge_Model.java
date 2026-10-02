package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.Entity;

public class Void_Forge_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox handle;
   private final AdvancedModelBox voidspike;
   private final AdvancedModelBox voidspike2;
   private final AdvancedModelBox voidspike3;
   private final AdvancedModelBox voidspike4;

   public Void_Forge_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.011F, 10.0F, 0.0F);
      this.root.setTextureOffset(0, 0).addBox(-3.011F, -13.25F, -3.5F, 6.0F, 6.0F, 8.0F, 0.0F, false);
      this.root.setTextureOffset(22, 24).addBox(-3.511F, -14.0F, 2.3F, 7.0F, 7.0F, 5.0F, 0.0F, false);
      this.root.setTextureOffset(15, 37).addBox(-2.511F, -14.0F, -8.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
      this.root.setTextureOffset(21, 0).addBox(-2.511F, -8.0F, -8.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
      this.root.setTextureOffset(0, 29).addBox(2.489F, -14.0F, -8.0F, 1.0F, 7.0F, 6.0F, 0.0F, false);
      this.root.setTextureOffset(0, 29).addBox(-3.511F, -14.0F, -8.0F, 1.0F, 7.0F, 6.0F, 0.0F, false);
      this.root.setTextureOffset(42, 20).addBox(-2.511F, -13.0F, -6.0F, 5.0F, 5.0F, 1.0F, 0.0F, false);
      this.root.setTextureOffset(0, 15).addBox(-3.511F, -14.0F, -8.25F, 7.0F, 7.0F, 6.0F, 0.25F, false);
      this.root.setTextureOffset(0, 43).addBox(-2.5F, -13.0F, -5.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);
      this.root.setTextureOffset(22, 8).addBox(-2.011F, -12.5F, 7.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);
      this.handle = new AdvancedModelBox(this);
      this.handle.setRotationPoint(-18.5F, -8.0F, 7.0F);
      this.root.addChild(this.handle);
      this.handle.setTextureOffset(37, 0).addBox(17.989F, 0.0F, -8.0F, 1.0F, 2.0F, 2.0F, 0.25F, false);
      this.handle.setTextureOffset(36, 37).addBox(17.989F, 0.0F, -8.0F, 1.0F, 17.0F, 2.0F, 0.0F, false);
      this.handle.setTextureOffset(0, 0).addBox(17.989F, 17.25F, -8.0F, 1.0F, 4.0F, 2.0F, 0.25F, false);
      this.voidspike = new AdvancedModelBox(this);
      this.voidspike.setRotationPoint(-0.286F, -14.9249F, 7.1558F);
      this.root.addChild(this.voidspike);
      this.setRotationAngle(this.voidspike, 0.7854F, 0.0F, 0.0F);
      this.voidspike.setTextureOffset(38, 1).addBox(-1.0F, -0.5F, -1.75F, 2.0F, 2.0F, 6.0F, 0.0F, false);
      this.voidspike.setTextureOffset(9, 29).addBox(-1.75F, -1.5F, -2.75F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.voidspike2 = new AdvancedModelBox(this);
      this.voidspike2.setRotationPoint(-0.186F, -6.0751F, 7.1558F);
      this.root.addChild(this.voidspike2);
      this.setRotationAngle(this.voidspike2, -0.829F, 0.0F, 0.0F);
      this.voidspike2.setTextureOffset(38, 1).addBox(-1.0F, -1.5F, -1.75F, 2.0F, 2.0F, 6.0F, 0.0F, false);
      this.voidspike2.setTextureOffset(9, 29).addBox(-1.75F, -0.5F, -2.75F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.voidspike3 = new AdvancedModelBox(this);
      this.voidspike3.setRotationPoint(3.739F, -10.0751F, 7.1558F);
      this.root.addChild(this.voidspike3);
      this.setRotationAngle(this.voidspike3, 0.0F, 0.7854F, 0.0F);
      this.voidspike3.setTextureOffset(38, 1).addBox(-0.5F, -1.5F, -1.75F, 2.0F, 2.0F, 6.0F, 0.0F, false);
      this.voidspike3.setTextureOffset(9, 29).addBox(-0.75F, -0.5F, -2.75F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.voidspike4 = new AdvancedModelBox(this);
      this.voidspike4.setRotationPoint(-3.75F, -10.0751F, 7.1558F);
      this.root.addChild(this.voidspike4);
      this.setRotationAngle(this.voidspike4, 0.0F, -0.829F, 0.0F);
      this.voidspike4.setTextureOffset(38, 1).addBox(-1.5F, -1.5F, -1.75F, 2.0F, 2.0F, 6.0F, 0.0F, false);
      this.voidspike4.setTextureOffset(9, 29).addBox(-2.25F, -0.5F, -2.75F, 3.0F, 2.0F, 3.0F, 0.0F, false);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.render(matrixStack, buffer, packedLight, packedOverlay);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.handle, this.voidspike, this.voidspike2, this.voidspike3, this.voidspike4);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
