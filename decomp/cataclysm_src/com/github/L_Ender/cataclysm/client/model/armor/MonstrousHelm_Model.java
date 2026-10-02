package com.github.L_Ender.cataclysm.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MonstrousHelm_Model extends HumanoidModel {
   public ModelPart helmet;

   public MonstrousHelm_Model(ModelPart p_170677_) {
      super(p_170677_);
      this.helmet = p_170677_.m_171324_("head").m_171324_("helmet");
   }

   public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171597_("head");
      PartDefinition helmet = head.m_171599_(
         "helmet",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.6F)),
         PartPose.m_171419_(0.0F, 1.0F, 0.0F)
      );
      helmet.m_171599_(
         "lefthorn",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 28)
            .m_171488_(4.0F, -6.0F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 16)
            .m_171488_(6.0F, -9.0F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      helmet.m_171599_(
         "righthorn",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 28)
            .m_171480_()
            .m_171488_(-6.0F, -6.0F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 16)
            .m_171480_()
            .m_171488_(-9.0F, -9.0F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }
}
