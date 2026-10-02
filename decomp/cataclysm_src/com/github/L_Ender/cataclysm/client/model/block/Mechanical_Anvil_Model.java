package com.github.L_Ender.cataclysm.client.model.block;

import com.github.L_Ender.cataclysm.blockentities.Mechanical_fusion_Anvil_Block_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Mechanical_Anvil_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox gear;

   public Mechanical_Anvil_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(0, 23).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 4.0F, 12.0F, 0.0F, false);
      this.root.setTextureOffset(37, 0).addBox(-4.0F, -5.0F, -5.0F, 8.0F, 1.0F, 10.0F, 0.0F, false);
      this.root.setTextureOffset(0, 40).addBox(-2.0F, -10.0F, -4.0F, 4.0F, 5.0F, 8.0F, 0.0F, false);
      this.root.setTextureOffset(0, 0).addBox(-5.0F, -16.0F, -8.0F, 10.0F, 6.0F, 16.0F, 0.0F, false);
      this.gear = new AdvancedModelBox(this);
      this.gear.setRotationPoint(5.0F, -13.0F, 0.0F);
      this.root.addChild(this.gear);
      this.gear.setTextureOffset(0, 0).addBox(-0.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, false);
      this.gear.setTextureOffset(0, 7).addBox(0.25F, 1.5F, -1.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
      this.gear.setTextureOffset(0, 7).addBox(0.25F, -2.5F, -1.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
      this.gear.setTextureOffset(0, 7).addBox(0.25F, 1.5F, 0.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
      this.gear.setTextureOffset(0, 7).addBox(0.25F, -2.5F, 0.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
      this.gear.setTextureOffset(0, 7).addBox(0.25F, 0.3F, 1.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
      this.gear.setTextureOffset(0, 7).addBox(0.25F, 0.3F, -2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
      this.gear.setTextureOffset(0, 7).addBox(0.25F, -1.3F, 1.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
      this.gear.setTextureOffset(0, 7).addBox(0.25F, -1.3F, -2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.gear);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public void animate(Mechanical_fusion_Anvil_Block_Entity beak, float partialTick) {
      this.resetToDefaultPose();
      float ageInTicks = (float)beak.tickCount + partialTick;
      this.gear.rotateAngleX -= ageInTicks * 0.025F;
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
