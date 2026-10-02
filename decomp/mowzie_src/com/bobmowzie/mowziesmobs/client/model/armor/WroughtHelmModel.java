package com.bobmowzie.mowziesmobs.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WroughtHelmModel<T extends LivingEntity> extends HumanoidModel<T> {
   public WroughtHelmModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createArmorLayer() {
      CubeDeformation deformation = CubeDeformation.f_171458_;
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171597_("head");
      PartDefinition tuskRight1 = head.m_171599_(
         "tuskRight1",
         CubeListBuilder.m_171558_().m_171514_(40, 24).m_171488_(-1.5F, -1.5F, -5.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.5F, -1.5F, -2.5F, 0.4363F, 0.7854F, 0.0F)
      );
      PartDefinition tuskRight2 = tuskRight1.m_171599_(
         "tuskRight2",
         CubeListBuilder.m_171558_().m_171514_(34, 4).m_171488_(-2.0F, -2.0F, -5.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(1.0F, 1.5F, -5.0F, -0.8727F, 0.0F, 0.0F)
      );
      PartDefinition hornRight1 = head.m_171599_(
         "hornRight1",
         CubeListBuilder.m_171558_().m_171514_(8, 3).m_171480_().m_171488_(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(3.0F, -8.0F, -3.0F, -0.3491F, -0.7854F, 0.0F)
      );
      PartDefinition hornLeft = hornRight1.m_171599_(
         "hornLeft",
         CubeListBuilder.m_171558_().m_171514_(43, 12).m_171480_().m_171488_(0.0F, -2.0F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-1.0F, 1.5F, -6.0F, -1.2217F, 0.0F, 0.0F)
      );
      PartDefinition tuskLeft1 = head.m_171599_(
         "tuskLeft1",
         CubeListBuilder.m_171558_().m_171514_(40, 24).m_171488_(-1.5F, -1.5F, -5.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.5F, -1.5F, -2.5F, 0.4363F, -0.7854F, 0.0F)
      );
      PartDefinition tuskLeft2 = tuskLeft1.m_171599_(
         "tuskLeft2",
         CubeListBuilder.m_171558_().m_171514_(34, 4).m_171488_(-2.0F, -2.0F, -5.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(1.0F, 1.5F, -5.0F, -0.8727F, 0.0F, 0.0F)
      );
      PartDefinition hornLeft1 = head.m_171599_(
         "hornLeft1",
         CubeListBuilder.m_171558_().m_171514_(8, 3).m_171480_().m_171488_(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-3.0F, -8.0F, -3.0F, -0.3491F, 0.7854F, 0.0F)
      );
      PartDefinition hornRight = hornLeft1.m_171599_(
         "hornRight",
         CubeListBuilder.m_171558_().m_171514_(30, 12).m_171480_().m_171488_(0.0F, -2.0F, -8.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-1.0F, 1.5F, -6.0F, -1.2217F, 0.0F, 0.0F)
      );
      PartDefinition shape1 = head.m_171599_(
         "shape1",
         CubeListBuilder.m_171558_().m_171514_(0, 12).m_171488_(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }
}
