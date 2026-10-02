package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.phys.Vec3;

public class Flare_Bomb_Model extends HierarchicalModel<Flare_Bomb_Entity> {
   private final ModelPart root;
   private final ModelPart outer;
   private final ModelPart inner;

   public Flare_Bomb_Model(ModelPart root) {
      this.root = root.m_171324_("root");
      this.outer = this.root.m_171324_("outer");
      this.inner = this.root.m_171324_("inner");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition root = partdefinition.m_171599_("root", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 4.0F, 0.0F));
      PartDefinition outer = root.m_171599_(
         "outer",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition inner = root.m_171599_(
         "inner",
         CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-4.5F, -4.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(Flare_Bomb_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float delta = ageInTicks - (float)entity.f_19797_;
      Vec3 prevV = new Vec3(entity.prevDeltaMovementX, entity.prevDeltaMovementY, entity.prevDeltaMovementZ);
      Vec3 dv = prevV.m_82549_(entity.m_20184_().m_82546_(prevV).m_82490_((double)delta));
      double d = Math.sqrt(dv.f_82479_ * dv.f_82479_ + dv.f_82480_ * dv.f_82480_ + dv.f_82481_ * dv.f_82481_);
      if (d != 0.0) {
         double a = dv.f_82480_ / d;
         a = Math.max(-10.0, Math.min(1.0, a));
         float pitch = -((float)Math.asin(a));
         this.root.f_104203_ = pitch + (float) (Math.PI / 2);
      }

      this.inner.f_104204_ = ageInTicks * 20.0F * (float) (Math.PI / 180.0);
      this.inner.f_104203_ = ageInTicks * 20.0F * (float) (Math.PI / 180.0);
      this.inner.f_104205_ = ageInTicks * 20.0F * (float) (Math.PI / 180.0);
      this.outer.f_104204_ = ageInTicks * -10.0F * (float) (Math.PI / 180.0);
      this.outer.f_104203_ = ageInTicks * -10.0F * (float) (Math.PI / 180.0);
      this.outer.f_104205_ = ageInTicks * -10.0F * (float) (Math.PI / 180.0);
   }

   public ModelPart m_142109_() {
      return this.root;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
