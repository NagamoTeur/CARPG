package net.thirdlife.iterrpg.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modelgiant_spider<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelgiant_spider"), "main");
   public final ModelPart spider;
   public final ModelPart body;
   public final ModelPart head;
   public final ModelPart leg_right1;
   public final ModelPart leg_right2;
   public final ModelPart leg_right3;
   public final ModelPart leg_right4;
   public final ModelPart leg_left1;
   public final ModelPart leg_left2;
   public final ModelPart leg_left3;
   public final ModelPart leg_left4;
   public final ModelPart brushko;
   public final ModelPart heli_left;
   public final ModelPart heli_right;

   public Modelgiant_spider(ModelPart root) {
      this.spider = root.m_171324_("spider");
      this.body = this.spider.m_171324_("body");
      this.head = this.spider.m_171324_("head");
      this.leg_right1 = this.spider.m_171324_("leg_right1");
      this.leg_right2 = this.spider.m_171324_("leg_right2");
      this.leg_right3 = this.spider.m_171324_("leg_right3");
      this.leg_right4 = this.spider.m_171324_("leg_right4");
      this.leg_left1 = this.spider.m_171324_("leg_left1");
      this.leg_left2 = this.spider.m_171324_("leg_left2");
      this.leg_left3 = this.spider.m_171324_("leg_left3");
      this.leg_left4 = this.spider.m_171324_("leg_left4");
      this.brushko = this.body.m_171324_("brushko");
      this.heli_left = this.head.m_171324_("heli_left");
      this.heli_right = this.head.m_171324_("heli_right");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition spider = partdefinition.m_171599_("spider", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition head = spider.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(46, 0).m_171488_(-3.0F, -3.0F, -6.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -13.0F, -4.0F)
      );
      PartDefinition heli_left = head.m_171599_(
         "heli_left",
         CubeListBuilder.m_171558_()
            .m_171514_(30, 32)
            .m_171480_()
            .m_171488_(-1.45F, -2.0F, -2.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(2.2F, 2.2F, -5.0F, 0.2182F, -0.3491F, 0.3927F)
      );
      PartDefinition heli_right = head.m_171599_(
         "heli_right",
         CubeListBuilder.m_171558_().m_171514_(30, 32).m_171488_(-1.55F, -2.0F, -2.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.2F, 2.2F, -5.0F, 0.2182F, 0.3491F, -0.3927F)
      );
      PartDefinition body = spider.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-5.0F, -4.5F, -4.5F, 10.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-0.25F, -12.0F, 0.0F)
      );
      PartDefinition brushko = body.m_171599_("brushko", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -1.0F, 4.0F));
      PartDefinition body3_r1 = brushko.m_171599_(
         "body3_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-7.0F, -11.0F, -3.0F, 14.0F, 14.0F, 18.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.5F, 1.5F, -0.3491F, 0.0F, 0.0F)
      );
      PartDefinition leg_left1 = spider.m_171599_(
         "leg_left1",
         CubeListBuilder.m_171558_().m_171514_(64, 24).m_171488_(-0.75F, -1.25F, -1.3F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0F, -11.0F, -3.7F, -0.1745F, 0.5411F, -0.3491F)
      );
      PartDefinition leg_left1_3 = leg_left1.m_171599_(
         "leg_left1_3",
         CubeListBuilder.m_171558_()
            .m_171514_(64, 65)
            .m_171488_(6.75F, -4.25F, -6.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 69)
            .m_171488_(7.75F, 8.75F, -5.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.0F, 2.0F, 3.7F)
      );
      PartDefinition leg_left2 = spider.m_171599_(
         "leg_left2",
         CubeListBuilder.m_171558_().m_171514_(64, 47).m_171488_(-1.25F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0F, -11.0F, -0.5F, 0.0F, 0.1571F, -0.3491F)
      );
      PartDefinition leg_left3_2 = leg_left2.m_171599_(
         "leg_left3_2",
         CubeListBuilder.m_171558_()
            .m_171514_(60, 28)
            .m_171488_(10.25F, -4.0F, -3.5F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(30, 38)
            .m_171488_(11.25F, 11.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.0F, 2.0F, 1.5F)
      );
      PartDefinition leg_left3 = spider.m_171599_(
         "leg_left3",
         CubeListBuilder.m_171558_().m_171514_(68, 10).m_171488_(-0.25F, -1.25F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(6.0F, -11.0F, 2.0F, 0.1222F, -0.1571F, -0.5236F)
      );
      PartDefinition leg_left2_2 = leg_left3.m_171599_(
         "leg_left2_2",
         CubeListBuilder.m_171558_()
            .m_171514_(52, 49)
            .m_171488_(14.75F, -2.25F, -1.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 69)
            .m_171488_(15.75F, 13.75F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 0.0F, -1.0F)
      );
      PartDefinition leg_left4 = spider.m_171599_(
         "leg_left4",
         CubeListBuilder.m_171558_().m_171514_(68, 51).m_171488_(-1.0F, -1.25F, -0.75F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(6.0F, -11.0F, 3.0F, 0.3491F, -0.5672F, -0.6545F)
      );
      PartDefinition leg_left1_2 = leg_left4.m_171599_(
         "leg_left1_2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 49)
            .m_171488_(15.0F, -1.25F, 0.25F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 69)
            .m_171488_(16.0F, 15.75F, 1.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-7.0F, -1.0F, -2.0F)
      );
      PartDefinition leg_right1 = spider.m_171599_(
         "leg_right1",
         CubeListBuilder.m_171558_()
            .m_171514_(64, 24)
            .m_171480_()
            .m_171488_(-9.25F, -1.25F, -1.3F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-5.0F, -11.0F, -3.7F, -0.1745F, -0.5411F, 0.3491F)
      );
      PartDefinition leg_left1_4 = leg_right1.m_171599_(
         "leg_left1_4",
         CubeListBuilder.m_171558_()
            .m_171514_(64, 65)
            .m_171480_()
            .m_171488_(-10.75F, -4.25F, -6.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(22, 69)
            .m_171480_()
            .m_171488_(-9.75F, 8.75F, -5.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(-1.0F, 2.0F, 3.7F)
      );
      PartDefinition leg_right2 = spider.m_171599_(
         "leg_right2",
         CubeListBuilder.m_171558_()
            .m_171514_(64, 47)
            .m_171480_()
            .m_171488_(-8.75F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-5.0F, -11.0F, -0.5F, 0.0F, -0.1571F, 0.3491F)
      );
      PartDefinition leg_right3_3 = leg_right2.m_171599_(
         "leg_right3_3",
         CubeListBuilder.m_171558_()
            .m_171514_(60, 28)
            .m_171480_()
            .m_171488_(-14.25F, -4.0F, -3.5F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(30, 38)
            .m_171480_()
            .m_171488_(-13.25F, 11.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(2.0F, 2.0F, 1.5F)
      );
      PartDefinition leg_right3 = spider.m_171599_(
         "leg_right3",
         CubeListBuilder.m_171558_()
            .m_171514_(68, 10)
            .m_171480_()
            .m_171488_(-8.75F, -1.25F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-6.0F, -11.0F, 2.0F, 0.1222F, 0.1571F, 0.5236F)
      );
      PartDefinition leg_right_3 = leg_right3.m_171599_(
         "leg_right_3",
         CubeListBuilder.m_171558_()
            .m_171514_(52, 49)
            .m_171480_()
            .m_171488_(-17.75F, -2.25F, -1.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(48, 69)
            .m_171480_()
            .m_171488_(-16.75F, 13.75F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(5.0F, 0.0F, -1.0F)
      );
      PartDefinition leg_right4 = spider.m_171599_(
         "leg_right4",
         CubeListBuilder.m_171558_()
            .m_171514_(68, 51)
            .m_171480_()
            .m_171488_(-9.0F, -1.25F, -0.75F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-6.0F, -11.0F, 3.0F, 0.3491F, 0.5672F, 0.6545F)
      );
      PartDefinition leg_right1_4 = leg_right4.m_171599_(
         "leg_right1_4",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 49)
            .m_171480_()
            .m_171488_(-19.0F, -1.25F, 0.25F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(56, 69)
            .m_171480_()
            .m_171488_(-18.0F, 15.75F, 1.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(7.0F, -1.0F, -2.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.body.f_104203_ = Mth.m_14031_(ageInTicks / 16.0F + (float) (Math.PI / 4)) / 18.0F;
      this.brushko.f_104203_ = Mth.m_14031_(ageInTicks / 16.0F) / 18.0F;
      this.heli_right.f_104203_ = Mth.m_14031_(ageInTicks / 14.0F) / 16.0F;
      this.heli_left.f_104203_ = Mth.m_14031_(ageInTicks / 14.0F) / 16.0F;
      this.leg_right1.f_104205_ = (float) (Math.PI / 12) + Mth.m_14031_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_right1.f_104204_ = (float) (-Math.PI / 6) + Mth.m_14089_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_right2.f_104205_ = (float) (Math.PI / 12) + Mth.m_14031_(limbSwing * 1.0F) * -0.5F * limbSwingAmount;
      this.leg_right2.f_104204_ = (float) (-Math.PI / 20) - Mth.m_14089_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_right3.f_104205_ = 0.43633232F + Mth.m_14031_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_right3.f_104204_ = (float) (Math.PI / 20) + Mth.m_14089_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_right4.f_104205_ = (float) (Math.PI / 6) + Mth.m_14031_(limbSwing * 1.0F) * -0.5F * limbSwingAmount;
      this.leg_right4.f_104204_ = (float) (Math.PI / 6) - Mth.m_14089_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_left1.f_104205_ = (float) (-Math.PI / 12) - Mth.m_14031_(limbSwing * 1.0F) * -0.5F * limbSwingAmount;
      this.leg_left1.f_104204_ = (float) (Math.PI / 6) + Mth.m_14089_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_left2.f_104205_ = (float) (-Math.PI / 12) - Mth.m_14031_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_left2.f_104204_ = (float) (Math.PI / 20) - Mth.m_14089_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_left3.f_104205_ = -0.43633232F - Mth.m_14031_(limbSwing * 1.0F) * -0.5F * limbSwingAmount;
      this.leg_left3.f_104204_ = (float) (-Math.PI / 20) + Mth.m_14089_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_left4.f_104205_ = (float) (-Math.PI / 6) - Mth.m_14031_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
      this.leg_left4.f_104204_ = (float) (-Math.PI / 6) - Mth.m_14089_(limbSwing * 1.0F) * 0.5F * limbSwingAmount;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.spider.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
