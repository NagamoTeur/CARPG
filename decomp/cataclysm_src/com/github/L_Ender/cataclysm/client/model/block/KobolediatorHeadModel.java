package com.github.L_Ender.cataclysm.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KobolediatorHeadModel extends Cataclysm_Skull_Model_Base {
   private final ModelPart head;
   private final ModelPart jaw;

   public KobolediatorHeadModel(ModelPart p_171097_) {
      this.head = p_171097_.m_171324_("head");
      this.jaw = this.head.m_171324_("jaw");
   }

   public static LayerDefinition createHeadLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(24, 119).m_171488_(-5.0F, -9.0F, -6.0513F, 10.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 8.0F, 0.0F)
      );
      PartDefinition head_cube1 = head.m_171599_(
         "head_cube1",
         CubeListBuilder.m_171558_().m_171514_(36, 100).m_171488_(0.8F, -5.0F, -8.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.7831F, -8.0F, 1.9487F, 0.1616F, 0.1866F, -0.0568F)
      );
      PartDefinition head_cube2 = head.m_171599_(
         "head_cube2",
         CubeListBuilder.m_171558_().m_171514_(62, 38).m_171488_(1.0F, -6.0F, -12.0F, 6.0F, 6.0F, 28.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-4.0F, -9.0F, 1.9487F, 0.48F, 0.0F, 0.0F)
      );
      PartDefinition head_cube3 = head.m_171599_(
         "head_cube3",
         CubeListBuilder.m_171558_().m_171514_(125, 113).m_171488_(-6.8F, -5.0F, -8.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.7832F, -8.0F, 1.9487F, 0.1616F, -0.1866F, 0.0568F)
      );
      PartDefinition head_cube4 = head.m_171599_(
         "head_cube4",
         CubeListBuilder.m_171558_().m_171514_(102, 49).m_171488_(-3.0F, -34.0F, -23.0F, 9.0F, 7.0F, 10.0F, new CubeDeformation(-0.01F)),
         PartPose.m_171423_(-1.5F, 24.0F, 8.9487F, 0.0436F, 0.0F, 0.0F)
      );
      PartDefinition right_horn = head.m_171599_(
         "right_horn",
         CubeListBuilder.m_171558_()
            .m_171514_(148, 105)
            .m_171488_(-9.2168F, -9.0F, 4.0513F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(148, 40)
            .m_171488_(-9.2168F, -9.0F, -1.9487F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(129, 0)
            .m_171488_(-9.2168F, -3.0F, -1.9487F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.7832F, -11.0F, 0.9487F)
      );
      PartDefinition left_horn = head.m_171599_(
         "left_horn",
         CubeListBuilder.m_171558_()
            .m_171514_(148, 52)
            .m_171488_(4.2168F, -9.0F, 4.0513F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(96, 146)
            .m_171488_(3.2168F, -9.0F, -1.9487F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(123, 93)
            .m_171488_(-2.7832F, -3.0F, -1.9487F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(2.7831F, -11.0F, 0.9487F)
      );
      PartDefinition jaw = head.m_171599_(
         "jaw",
         CubeListBuilder.m_171558_().m_171514_(102, 29).m_171488_(-2.7168F, -4.0F, -12.0F, 7.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-0.7832F, 0.0F, -2.0513F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public KobolediatorHeadModel withAnimations(LivingEntity entity) {
      float partialTick = Minecraft.m_91087_().m_91296_();
      float limbSwingAmount = Mth.m_14179_(partialTick, entity.f_20923_, entity.f_20924_);
      float limbSwing = entity.f_20925_ + partialTick;
      this.setupAnim((float)entity.f_19797_ + partialTick, 0.0F, 0.0F);
      return this;
   }

   @Override
   public void setupAnim(float p_104188_, float p_104189_, float p_104190_) {
      this.jaw.f_104203_ = (float)(Math.sin((double)(p_104188_ * (float) Math.PI * 0.2F)) + 1.0) * 0.2F;
      this.head.f_104204_ = p_104189_ * (float) (Math.PI / 180.0);
      this.head.f_104203_ = p_104190_ * (float) (Math.PI / 180.0);
   }

   public void m_7695_(
      PoseStack p_104192_, VertexConsumer p_104193_, int p_104194_, int p_104195_, float p_104196_, float p_104197_, float p_104198_, float p_104199_
   ) {
      p_104192_.m_85836_();
      p_104192_.m_85837_(0.0, -0.374375F, 0.0);
      p_104192_.m_85841_(0.75F, 0.75F, 0.75F);
      this.head.m_104306_(p_104192_, p_104193_, p_104194_, p_104195_, p_104196_, p_104197_, p_104198_, p_104199_);
      p_104192_.m_85849_();
   }
}
