package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.Entity;

public class Gauntlet_of_Bulwark_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox gauntlet_fist;
   private final AdvancedModelBox void_stone_knuckle;
   private final AdvancedModelBox small_spike2;
   private final AdvancedModelBox gauntlet_arm;
   private final AdvancedModelBox gauntlet_arm2;
   private final AdvancedModelBox gauntlet_shoulder;
   private final AdvancedModelBox sholder_deco;
   private final AdvancedModelBox big_spike;
   private final AdvancedModelBox small_spike;
   private final AdvancedModelBox sholder_deco2;

   public Gauntlet_of_Bulwark_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 19.0F, -3.5F);
      this.gauntlet_fist = new AdvancedModelBox(this);
      this.gauntlet_fist.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addChild(this.gauntlet_fist);
      this.gauntlet_fist.setTextureOffset(24, 30).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.25F, false);
      this.void_stone_knuckle = new AdvancedModelBox(this);
      this.void_stone_knuckle.setRotationPoint(-5.25F, -2.5F, -6.0F);
      this.gauntlet_fist.addChild(this.void_stone_knuckle);
      this.void_stone_knuckle.setTextureOffset(0, 0).addBox(0.0F, 0.5F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
      this.small_spike2 = new AdvancedModelBox(this);
      this.small_spike2.setRotationPoint(-7.0F, -1.0F, -6.75F);
      this.gauntlet_fist.addChild(this.small_spike2);
      this.setRotationAngle(this.small_spike2, 0.0F, -1.1345F, 0.0F);
      this.small_spike2.setTextureOffset(46, 60).addBox(-4.0F, -1.25F, -5.5F, 7.0F, 0.0F, 4.0F, 0.0F, false);
      this.small_spike2.setTextureOffset(46, 60).addBox(-4.0F, 3.25F, -5.5F, 7.0F, 0.0F, 4.0F, 0.0F, false);
      this.gauntlet_arm = new AdvancedModelBox(this);
      this.gauntlet_arm.setRotationPoint(0.0F, 0.0F, 6.0F);
      this.root.addChild(this.gauntlet_arm);
      this.gauntlet_arm.setTextureOffset(0, 22).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
      this.gauntlet_arm2 = new AdvancedModelBox(this);
      this.gauntlet_arm2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addChild(this.gauntlet_arm2);
      this.gauntlet_arm2.setTextureOffset(34, 16).addBox(-4.0F, -4.0F, 0.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);
      this.gauntlet_shoulder = new AdvancedModelBox(this);
      this.gauntlet_shoulder.setRotationPoint(2.0F, 0.0F, 10.0F);
      this.gauntlet_arm2.addChild(this.gauntlet_shoulder);
      this.setRotationAngle(this.gauntlet_shoulder, 0.0F, 0.3927F, 0.0F);
      this.gauntlet_shoulder.setTextureOffset(0, 0).addBox(-8.0F, -5.0F, -6.0F, 8.0F, 10.0F, 12.0F, 0.0F, false);
      this.sholder_deco = new AdvancedModelBox(this);
      this.sholder_deco.setRotationPoint(6.5F, -7.0F, 0.0F);
      this.gauntlet_shoulder.addChild(this.sholder_deco);
      this.sholder_deco.setTextureOffset(20, 46).addBox(-8.5F, 1.0F, -5.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
      this.big_spike = new AdvancedModelBox(this);
      this.big_spike.setRotationPoint(-6.0F, 0.0F, 4.0F);
      this.gauntlet_shoulder.addChild(this.big_spike);
      this.setRotationAngle(this.big_spike, 0.0F, 2.3998F, 0.0F);
      this.big_spike.setTextureOffset(-11, 53).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 11.0F, 0.0F, false);
      this.small_spike = new AdvancedModelBox(this);
      this.small_spike.setRotationPoint(-9.0F, 2.0F, -1.0F);
      this.gauntlet_shoulder.addChild(this.small_spike);
      this.small_spike.setTextureOffset(46, 60).addBox(-6.0F, -2.0F, -3.0F, 7.0F, 0.0F, 4.0F, 0.0F, false);
      this.sholder_deco2 = new AdvancedModelBox(this);
      this.sholder_deco2.setRotationPoint(0.0F, 5.0F, -3.0F);
      this.gauntlet_shoulder.addChild(this.sholder_deco2);
      this.sholder_deco2.setTextureOffset(20, 46).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
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
      return ImmutableList.of(
         this.root,
         this.gauntlet_fist,
         this.void_stone_knuckle,
         this.gauntlet_arm,
         this.gauntlet_arm2,
         this.gauntlet_shoulder,
         this.small_spike2,
         this.sholder_deco,
         this.big_spike,
         this.small_spike,
         this.sholder_deco2
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
