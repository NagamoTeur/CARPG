package com.github.L_Ender.cataclysm.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
public class DraugrHeadModel extends Cataclysm_Skull_Model_Base {
   private final ModelPart head;
   private final ModelPart maw;

   public DraugrHeadModel(ModelPart p_171097_) {
      this.head = p_171097_.m_171324_("head");
      this.maw = this.head.m_171324_("maw");
   }

   public static LayerDefinition createHeadLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(92, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F))
            .m_171514_(0, 32)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
            .m_171514_(58, 36)
            .m_171488_(0.0F, -16.0F, 0.0F, 10.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 55)
            .m_171480_()
            .m_171488_(-10.0F, -13.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(0.0F, 5.0F, 0.0F)
      );
      PartDefinition maw = head.m_171599_("maw", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -2.5F, -1.0F, -0.0873F, 0.0F, 0.2182F));
      PartDefinition body_r1 = maw.m_171599_(
         "body_r1",
         CubeListBuilder.m_171558_().m_171514_(32, 6).m_171488_(-3.0F, 0.0F, -4.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   @Override
   public void setupAnim(float p_104188_, float p_104189_, float p_104190_) {
      this.head.f_104204_ = p_104189_ * (float) (Math.PI / 180.0);
      this.head.f_104203_ = p_104190_ * (float) (Math.PI / 180.0);
   }

   public void m_7695_(
      PoseStack p_104192_, VertexConsumer p_104193_, int p_104194_, int p_104195_, float p_104196_, float p_104197_, float p_104198_, float p_104199_
   ) {
      p_104192_.m_85836_();
      p_104192_.m_85837_(0.0, -0.374375F, 0.0);
      p_104192_.m_85841_(1.0F, 1.0F, 1.0F);
      this.head.m_104306_(p_104192_, p_104193_, p_104194_, p_104195_, p_104196_, p_104197_, p_104198_, p_104199_);
      p_104192_.m_85849_();
   }
}
