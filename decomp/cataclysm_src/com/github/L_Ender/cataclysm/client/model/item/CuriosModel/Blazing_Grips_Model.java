package com.github.L_Ender.cataclysm.client.model.item.CuriosModel;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class Blazing_Grips_Model extends HumanoidModel<LivingEntity> {
   public Blazing_Grips_Model(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createLayer(boolean slim, CubeDeformation deformation) {
      MeshDefinition meshDefinition = HumanoidModel.m_170681_(deformation, 0.0F);
      PartDefinition partDefinition = meshDefinition.m_171576_();
      PartDefinition rightArm = partDefinition.m_171597_("right_arm");
      PartDefinition leftArm = partDefinition.m_171597_("left_arm");
      float slimornot = slim ? 0.0F : 1.0F;
      rightArm.m_171599_(
         "right_gauntlet",
         CubeListBuilder.m_171558_()
            .m_171514_(63, 6)
            .m_171488_(-2.0F, 1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.4F))
            .m_171514_(63, 18)
            .m_171488_(-2.0F, 0.1F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.55F)),
         PartPose.m_171419_(-slimornot, 3.0F, 0.0F)
      );
      leftArm.m_171599_(
         "left_gauntlet",
         CubeListBuilder.m_171558_()
            .m_171514_(63, 6)
            .m_171480_()
            .m_171488_(-2.0F, 1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.4F))
            .m_171555_(false)
            .m_171514_(63, 18)
            .m_171480_()
            .m_171488_(-2.0F, 0.1F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.55F))
            .m_171555_(false),
         PartPose.m_171419_(slimornot, 3.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshDefinition, 128, 128);
   }

   public void renderArm(
      HumanoidArm handSide, PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      this.m_102851_(handSide).f_104207_ = true;
      this.m_102851_(handSide.m_20828_()).f_104207_ = false;
      this.m_7695_(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of();
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102812_, this.f_102811_);
   }
}
