package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Lava_Bomb_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.phys.Vec3;

public class Lava_Bomb_Model extends AdvancedEntityModel<Lava_Bomb_Entity> {
   private final AdvancedModelBox root;

   public Lava_Bomb_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.root.setTextureOffset(0, 0).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root);
   }

   public void setupAnim(Lava_Bomb_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float delta = ageInTicks - (float)entity.f_19797_;
      Vec3 prevV = new Vec3(entity.prevDeltaMovementX, entity.prevDeltaMovementY, entity.prevDeltaMovementZ);
      Vec3 dv = prevV.m_82549_(entity.m_20184_().m_82546_(prevV).m_82490_((double)delta));
      double d = Math.sqrt(dv.f_82479_ * dv.f_82479_ + dv.f_82480_ * dv.f_82480_ + dv.f_82481_ * dv.f_82481_);
      if (d != 0.0) {
         double a = dv.f_82480_ / d;
         a = Math.max(-10.0, Math.min(1.0, a));
         float pitch = -((float)Math.asin(a));
         this.root.rotateAngleX = pitch + (float) (Math.PI / 2);
      }
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
