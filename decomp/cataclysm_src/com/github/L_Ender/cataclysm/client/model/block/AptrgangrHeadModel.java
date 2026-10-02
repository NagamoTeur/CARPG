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
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AptrgangrHeadModel extends Cataclysm_Skull_Model_Base {
   private final ModelPart head;
   private final ModelPart helmet;
   private final ModelPart jaw;

   public AptrgangrHeadModel(ModelPart root) {
      this.head = root.m_171324_("head");
      this.helmet = this.head.m_171324_("helmet");
      this.jaw = this.head.m_171324_("jaw");
   }

   public static LayerDefinition createHeadLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(0, 111).m_171488_(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition helmet = head.m_171599_(
         "helmet",
         CubeListBuilder.m_171558_()
            .m_171514_(32, 113)
            .m_171488_(-4.0F, -2.0F, -3.5F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.5F))
            .m_171514_(102, 110)
            .m_171488_(-1.5F, -2.8F, -4.3F, 3.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 120)
            .m_171480_()
            .m_171488_(-5.5F, -2.0F, -1.5F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(116, 20)
            .m_171488_(-10.5F, -3.5F, 0.5F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 120)
            .m_171488_(4.5F, -2.0F, -1.5F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(116, 0)
            .m_171488_(4.5F, -9.5F, 0.5F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(88, 98)
            .m_171488_(-5.0F, 3.2F, -4.3F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.001F))
            .m_171514_(62, 91)
            .m_171488_(-4.0F, 5.0F, -3.5F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -7.0F, -0.5F)
      );
      PartDefinition head_r1 = helmet.m_171599_(
         "head_r1",
         CubeListBuilder.m_171558_().m_171514_(28, 104).m_171480_().m_171488_(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(2.4F, 3.5F, -3.8F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition head_r2 = helmet.m_171599_(
         "head_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(42, 111)
            .m_171480_()
            .m_171488_(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(44, 106)
            .m_171480_()
            .m_171488_(0.0F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(43, 109)
            .m_171480_()
            .m_171488_(-1.0F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(29, 115)
            .m_171480_()
            .m_171488_(-2.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(2.4F, 3.5F, -4.1F, 0.0F, 0.0F, -0.2618F)
      );
      PartDefinition head_r3 = helmet.m_171599_(
         "head_r3",
         CubeListBuilder.m_171558_()
            .m_171514_(43, 108)
            .m_171488_(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 104)
            .m_171488_(-2.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.4F, 3.5F, -4.1F, 0.0F, 0.0F, 0.2618F)
      );
      PartDefinition head_r4 = helmet.m_171599_(
         "head_r4",
         CubeListBuilder.m_171558_()
            .m_171514_(25, 108)
            .m_171480_()
            .m_171488_(-0.5F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 4.8F, -4.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition head_r5 = helmet.m_171599_(
         "head_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(31, 108)
            .m_171480_()
            .m_171488_(-0.5F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 6.2F, -4.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition head_r6 = helmet.m_171599_(
         "head_r6",
         CubeListBuilder.m_171558_()
            .m_171514_(30, 111)
            .m_171480_()
            .m_171488_(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 1.7F, -3.9F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition jaw = head.m_171599_(
         "jaw",
         CubeListBuilder.m_171558_()
            .m_171514_(34, 26)
            .m_171488_(-3.0F, 0.0F, -2.5F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 0)
            .m_171488_(3.0F, 3.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 5)
            .m_171488_(3.0F, -2.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 5)
            .m_171480_()
            .m_171488_(-5.0F, -2.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(48, 0)
            .m_171480_()
            .m_171488_(-9.0F, 3.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(84, 1)
            .m_171488_(3.0F, 8.0F, -2.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(84, 1)
            .m_171480_()
            .m_171488_(-8.0F, 8.0F, -2.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(52, 33)
            .m_171488_(-3.0F, 0.0F, 0.5F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -5.0F, -1.5F)
      );
      PartDefinition head_r7 = jaw.m_171599_(
         "head_r7",
         CubeListBuilder.m_171558_().m_171514_(92, 12).m_171488_(-3.0F, 0.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 8.0F, -2.5F, -0.3491F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   @Override
   public void setupAnim(float p_104188_, float p_104189_, float p_104190_) {
      this.jaw.f_104201_ = -3.0F + Mth.m_14031_(p_104188_ * 0.5F - 2.0F) * 2.0F;
      this.head.f_104204_ = p_104189_ * (float) (Math.PI / 180.0);
      this.head.f_104203_ = p_104190_ * (float) (Math.PI / 180.0);
   }

   public void m_7695_(
      PoseStack p_104192_, VertexConsumer p_104193_, int p_104194_, int p_104195_, float p_104196_, float p_104197_, float p_104198_, float p_104199_
   ) {
      p_104192_.m_85836_();
      p_104192_.m_85837_(0.0, -0.49916F, 0.0);
      p_104192_.m_85841_(1.0F, 1.0F, 1.0F);
      this.head.m_104306_(p_104192_, p_104193_, p_104194_, p_104195_, p_104196_, p_104197_, p_104198_, p_104199_);
      p_104192_.m_85849_();
   }
}
