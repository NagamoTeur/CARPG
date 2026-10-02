package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Nameless_Sorcerer_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;

public class Nameless_Sorcerer_Model extends AdvancedEntityModel<Nameless_Sorcerer_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox head;
   private final AdvancedModelBox headwear;
   private final AdvancedModelBox nose;
   private final AdvancedModelBox body;
   private final AdvancedModelBox bodywear;
   private final AdvancedModelBox right_leg;
   private final AdvancedModelBox left_leg;
   private final AdvancedModelBox right_arm;
   private final AdvancedModelBox book;
   private final AdvancedModelBox cover_right;
   private final AdvancedModelBox cover_left;
   private final AdvancedModelBox filpping_page_right;
   private final AdvancedModelBox page_right;
   private final AdvancedModelBox page_left;
   private final AdvancedModelBox filpping_page_left;
   private final AdvancedModelBox left_arm;
   private ModelAnimator animator;

   public Nameless_Sorcerer_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -24.0F, 0.0F);
      this.root.addChild(this.head);
      this.head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);
      this.headwear = new AdvancedModelBox(this);
      this.headwear.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.head.addChild(this.headwear);
      this.headwear.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.25F, false);
      this.nose = new AdvancedModelBox(this);
      this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
      this.head.addChild(this.nose);
      this.nose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0F, -24.0F, 0.0F);
      this.root.addChild(this.body);
      this.body.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, 0.0F, false);
      this.bodywear = new AdvancedModelBox(this);
      this.bodywear.setRotationPoint(0.0F, -24.0F, 0.0F);
      this.root.addChild(this.bodywear);
      this.bodywear.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0.5F, false);
      this.right_leg = new AdvancedModelBox(this);
      this.right_leg.setRotationPoint(-2.0F, -12.0F, 0.0F);
      this.root.addChild(this.right_leg);
      this.right_leg.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
      this.left_leg = new AdvancedModelBox(this);
      this.left_leg.setRotationPoint(2.0F, -12.0F, 0.0F);
      this.root.addChild(this.left_leg);
      this.left_leg.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
      this.right_arm = new AdvancedModelBox(this);
      this.right_arm.setRotationPoint(-4.0F, -22.0F, 0.0F);
      this.root.addChild(this.right_arm);
      this.right_arm.setTextureOffset(40, 46).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
      this.book = new AdvancedModelBox(this);
      this.book.setRotationPoint(-2.0F, 14.5F, 5.0F);
      this.right_arm.addChild(this.book);
      this.book.setTextureOffset(19, 55).addBox(-1.0F, 0.0F, -10.0F, 2.0F, 0.0F, 10.0F, 0.0F, false);
      this.cover_right = new AdvancedModelBox(this);
      this.cover_right.setRotationPoint(-1.0F, 0.0F, -5.0F);
      this.book.addChild(this.cover_right);
      this.setRotationAngle(this.cover_right, 0.0F, 0.0F, 1.5708F);
      this.cover_right.setTextureOffset(35, 27).addBox(-6.0F, 0.0F, -5.0F, 6.0F, 0.0F, 10.0F, 0.0F, false);
      this.cover_left = new AdvancedModelBox(this);
      this.cover_left.setRotationPoint(1.0F, 0.0F, -5.0F);
      this.book.addChild(this.cover_left);
      this.setRotationAngle(this.cover_left, 0.0F, 0.0F, -1.5708F);
      this.cover_left.setTextureOffset(47, 27).addBox(0.0F, 0.0F, -5.0F, 6.0F, 0.0F, 10.0F, 0.0F, false);
      this.filpping_page_right = new AdvancedModelBox(this);
      this.filpping_page_right.setRotationPoint(0.0F, -0.075F, -5.0F);
      this.book.addChild(this.filpping_page_right);
      this.setRotationAngle(this.filpping_page_right, 0.0F, 0.0F, 1.5708F);
      this.filpping_page_right.setTextureOffset(49, 46).addBox(-5.0F, -0.025F, -4.0F, 5.0F, 0.0F, 8.0F, 0.0F, false);
      this.page_right = new AdvancedModelBox(this);
      this.page_right.setRotationPoint(0.0F, -0.075F, -5.0F);
      this.book.addChild(this.page_right);
      this.setRotationAngle(this.page_right, 0.0F, 0.0F, 1.5708F);
      this.page_right.setTextureOffset(65, 0).addBox(-5.0F, -0.025F, -4.0F, 5.0F, 1.0F, 8.0F, 0.0F, false);
      this.page_left = new AdvancedModelBox(this);
      this.page_left.setRotationPoint(0.0F, -0.075F, -5.0F);
      this.book.addChild(this.page_left);
      this.setRotationAngle(this.page_left, 0.0F, 0.0F, -1.5708F);
      this.page_left.setTextureOffset(65, 9).addBox(0.0F, -0.025F, -4.0F, 5.0F, 1.0F, 8.0F, 0.0F, false);
      this.filpping_page_left = new AdvancedModelBox(this);
      this.filpping_page_left.setRotationPoint(0.0F, -0.075F, -5.0F);
      this.book.addChild(this.filpping_page_left);
      this.setRotationAngle(this.filpping_page_left, 0.0F, 0.0F, -1.5708F);
      this.filpping_page_left.setTextureOffset(49, 54).addBox(0.0F, -0.025F, -4.0F, 5.0F, 0.0F, 8.0F, 0.0F, false);
      this.left_arm = new AdvancedModelBox(this);
      this.left_arm.setRotationPoint(4.0F, -22.0F, 0.0F);
      this.root.addChild(this.left_arm);
      this.left_arm.setTextureOffset(40, 46).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
   }

   public void setupAnim(Nameless_Sorcerer_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.head.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.right_leg.rotateAngleX = Mth.m_14089_(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
      this.left_leg.rotateAngleX = Mth.m_14089_(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
      float f = 1.0F;
      this.right_arm.rotateAngleX = this.right_arm.rotateAngleX + Mth.m_14089_(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
      this.left_arm.rotateAngleX = this.left_arm.rotateAngleX + Mth.m_14089_(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
      this.right_arm.rotateAngleZ = this.right_arm.rotateAngleZ + Mth.m_14089_(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.left_arm.rotateAngleZ = this.left_arm.rotateAngleZ - (Mth.m_14089_(ageInTicks * 0.09F) * 0.05F + 0.05F);
      this.right_arm.rotateAngleX = this.right_arm.rotateAngleX + Mth.m_14031_(ageInTicks * 0.067F) * 0.05F;
      this.left_arm.rotateAngleX = this.left_arm.rotateAngleX - Mth.m_14031_(ageInTicks * 0.067F) * 0.05F;
      IllagerArmPose abstractillagerentity$armpose = entityIn.m_6768_();
      if (abstractillagerentity$armpose == IllagerArmPose.SPELLCASTING) {
         this.right_arm.rotationPointZ = 0.0F;
         this.right_arm.rotationPointX = -5.0F;
         this.left_arm.rotationPointZ = 0.0F;
         this.left_arm.rotationPointX = 5.0F;
         this.right_arm.rotateAngleX = Mth.m_14089_(ageInTicks * 0.6662F) * 0.25F;
         this.left_arm.rotateAngleX = Mth.m_14089_(ageInTicks * 0.6662F) * 0.25F;
         this.right_arm.rotateAngleZ = (float) (Math.PI * 3.0 / 4.0);
         this.left_arm.rotateAngleZ = (float) (-Math.PI * 3.0 / 4.0);
         this.right_arm.rotateAngleY = 0.0F;
         this.left_arm.rotateAngleY = 0.0F;
      }
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.head,
         this.nose,
         this.body,
         this.left_arm,
         this.right_arm,
         this.left_leg,
         this.right_leg,
         this.book,
         this.cover_left,
         this.cover_right,
         this.filpping_page_left,
         new AdvancedModelBox[]{this.filpping_page_right, this.page_left, this.page_right}
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
