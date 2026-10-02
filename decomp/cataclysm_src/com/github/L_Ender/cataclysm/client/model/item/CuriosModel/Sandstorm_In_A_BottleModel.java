package com.github.L_Ender.cataclysm.client.model.item.CuriosModel;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;

public class Sandstorm_In_A_BottleModel extends HumanoidModel<LivingEntity> {
   public Sandstorm_In_A_BottleModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createLayer(CubeDeformation deformation) {
      MeshDefinition meshDefinition = HumanoidModel.m_170681_(deformation, 0.0F);
      PartDefinition partDefinition = meshDefinition.m_171576_();
      PartDefinition body = partDefinition.m_171597_("body");
      PartDefinition root = body.m_171599_(
         "root",
         CubeListBuilder.m_171558_()
            .m_171514_(65, 10)
            .m_171488_(-4.0F, -1.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(63, 16)
            .m_171488_(-1.0F, 1.0F, -2.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 9.0F, 0.0F)
      );
      root.m_171599_(
         "bottle",
         CubeListBuilder.m_171558_()
            .m_171514_(75, 24)
            .m_171480_()
            .m_171488_(-1.5F, -0.5F, -1.45F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
            .m_171555_(false)
            .m_171514_(63, 24)
            .m_171480_()
            .m_171488_(-1.5F, -1.0F, -1.45F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(71, 33)
            .m_171480_()
            .m_171488_(-1.5F, 4.0F, -1.45F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(63, 35)
            .m_171480_()
            .m_171488_(-1.0F, 0.7F, -1.05F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F))
            .m_171555_(false)
            .m_171514_(63, 32)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -1.05F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-3.5F, 0.0F, 3.55F, 0.0F, 0.0F, 0.0436F)
      );
      root.m_171599_(
         "bottle2",
         CubeListBuilder.m_171558_()
            .m_171514_(75, 24)
            .m_171488_(-1.5F, -0.5F, -1.45F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
            .m_171514_(63, 24)
            .m_171488_(-1.5F, -1.0F, -1.45F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(63, 35)
            .m_171488_(-1.0F, 0.7F, -1.05F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F))
            .m_171514_(63, 32)
            .m_171488_(-1.0F, -2.0F, -1.05F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(83, 33)
            .m_171488_(-1.5F, 4.0F, -1.45F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.5F, 0.0F, 3.55F, 0.0F, 0.0F, -0.0436F)
      );
      root.m_171599_(
         "belt",
         CubeListBuilder.m_171558_().m_171514_(63, 19).m_171488_(-0.9412F, -2.0F, -0.1341F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.5F, 0.0F, -2.0F, 0.0F, -0.2618F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshDefinition, 128, 128);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of();
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102810_);
   }
}
