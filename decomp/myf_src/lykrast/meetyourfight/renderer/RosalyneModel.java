package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RosalyneEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class RosalyneModel extends EntityModel<RosalyneEntity> {
   public static final ModelLayerLocation MODEL = new ModelLayerLocation(MeetYourFight.rl("rosalyne"), "main");
   public static final ModelLayerLocation MODEL_ARMOR = new ModelLayerLocation(MeetYourFight.rl("rosalyne"), "armor");
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart rightArm;
   private final ModelPart forearm;
   private final ModelPart blade;
   private final ModelPart leftArm;
   private final ModelPart rightLeg;
   private final ModelPart leftLeg;
   private final ModelPart coffin;
   private static final RosalyneModel.Pose[] RARM_POSE = new RosalyneModel.Pose[]{
      new RosalyneModel.Pose(-5.0F, 0.0F, 0.0F, 30.0F, 0.0F, 0.0F, 15.0F, 0.0F, 0.0F, false),
      new RosalyneModel.Pose(-10.0F, 0.0F, 100.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
      new RosalyneModel.Pose(105.0F, 0.0F, 95.0F, 80.0F, 0.0F, 0.0F, 30.0F, 0.0F, 0.0F),
      new RosalyneModel.Pose(-10.0F, 0.0F, 80.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
      new RosalyneModel.Pose(100.0F, 0.0F, 120.0F, 80.0F, 0.0F, 0.0F, 30.0F, 0.0F, 0.0F),
      new RosalyneModel.Pose(-5.0F, 0.0F, 40.0F, 30.0F, 0.0F, 0.0F, 30.0F, 0.0F, 0.0F),
      new RosalyneModel.Pose(-30.0F, 0.0F, 0.0F, 130.0F, 0.0F, 0.0F, -100.0F, 0.0F, 0.0F, false),
      new RosalyneModel.Pose(-5.0F, 0.0F, 0.0F, 30.0F, 0.0F, 0.0F, 15.0F, 0.0F, 0.0F, false),
      new RosalyneModel.Pose(30.0F, 35.0F, 0.0F, 110.0F, 0.0F, 0.0F, 85.0F, 0.0F, 0.0F, false),
      new RosalyneModel.Pose(150.0F, -10.0F, 0.0F, 35.0F, 0.0F, 0.0F, 5.0F, 0.0F, 0.0F),
      new RosalyneModel.Pose(20.0F, 20.0F, 0.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
   };
   private float animProgress;
   private int phase;
   private RosalyneModel.Pose pose;
   private RosalyneModel.Pose prevPose;

   public RosalyneModel(ModelPart root) {
      this.head = root.m_171324_("Head");
      this.body = root.m_171324_("Body");
      this.rightArm = root.m_171324_("RightArm");
      this.forearm = this.rightArm.m_171324_("Forearm");
      this.blade = this.forearm.m_171324_("Blade");
      this.leftArm = root.m_171324_("LeftArm");
      this.rightLeg = root.m_171324_("RightLeg");
      this.leftLeg = root.m_171324_("LeftLeg");
      this.coffin = root.m_171324_("Coffin");
   }

   public static LayerDefinition createBodyLayer(CubeDeformation deform, boolean hat) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      CubeListBuilder headcubes = CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deform);
      if (hat) {
         headcubes.m_171514_(32, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deform.m_171469_(0.25F));
      }

      partdefinition.m_171599_("Head", headcubes, PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      partdefinition.m_171599_(
         "Body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deform), PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, deform.m_171469_(0.25F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition Forearm = RightArm.m_171599_(
         "Forearm",
         CubeListBuilder.m_171558_().m_171514_(0, 34).m_171488_(-2.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, deform),
         PartPose.m_171419_(-1.0F, 10.0F, 0.0F)
      );
      Forearm.m_171599_(
         "Blade",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 32)
            .m_171488_(-0.5F, -6.0F, -3.0F, 1.0F, 20.0F, 6.0F, deform)
            .m_171514_(0, 52)
            .m_171488_(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, deform),
         PartPose.m_171419_(0.0F, 10.0F, 0.0F)
      );
      partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deform),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deform),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_().m_171514_(40, 48).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deform),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      partdefinition.m_171599_(
         "Coffin",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.0F, -32.0F, -8.0F, 16.0F, 32.0F, 16.0F, deform),
         PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void prepareMobModel(RosalyneEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
      super.m_6839_(entityIn, limbSwing, limbSwingAmount, partialTick);
      this.phase = entityIn.getPhase();
      this.pose = RARM_POSE[entityIn.clientAnim];
      this.prevPose = RARM_POSE[entityIn.prevAnim];
      this.animProgress = entityIn.getAnimProgress(partialTick);
      if (this.pose.fast) {
         this.animProgress = 1.0F - this.animProgress;
         this.animProgress = this.animProgress * this.animProgress;
         this.animProgress = this.animProgress * this.animProgress;
         this.animProgress = 1.0F - this.animProgress;
      }
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      if (this.phase != 0 && this.phase != 1) {
         this.head.m_104301_(poseStack, buffer, packedLight, packedOverlay);
         this.body.m_104301_(poseStack, buffer, packedLight, packedOverlay);
         this.rightArm.m_104301_(poseStack, buffer, packedLight, packedOverlay);
         this.leftArm.m_104301_(poseStack, buffer, packedLight, packedOverlay);
         this.rightLeg.m_104301_(poseStack, buffer, packedLight, packedOverlay);
         this.leftLeg.m_104301_(poseStack, buffer, packedLight, packedOverlay);
      } else {
         this.coffin.m_104301_(poseStack, buffer, packedLight, packedOverlay);
      }
   }

   public void setupAnim(RosalyneEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (this.phase != 0 && this.phase != 1) {
         this.head.f_104203_ = headPitch * (float) (Math.PI / 180.0);
         this.head.f_104204_ = netHeadYaw * (float) (Math.PI / 180.0);
         this.leftArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
         this.leftArm.f_104204_ = 0.0F;
         this.leftArm.f_104205_ = 0.0F;
         if (entityIn.clientAnim == 8) {
            if ((double)this.animProgress >= 0.99) {
               this.head.f_104203_ = -0.43633232F;
               this.head.f_104204_ = 0.0F;
               this.leftArm.f_104203_ = (float) (-Math.PI * 8.0 / 9.0);
               this.leftArm.f_104204_ = (float) (Math.PI / 6);
            } else {
               this.head.f_104203_ = this.rotlerpRad(this.animProgress, this.head.f_104203_, 0.43633232F);
               this.head.f_104204_ = this.rotlerpRad(this.animProgress, this.head.f_104204_, 0.0F);
               this.leftArm.f_104203_ = this.rotlerpRad(this.animProgress, this.leftArm.f_104203_, (float) (-Math.PI * 8.0 / 9.0));
               this.leftArm.f_104204_ = this.rotlerpRad(this.animProgress, this.leftArm.f_104204_, (float) (Math.PI / 6));
            }
         } else if (entityIn.prevAnim == 8) {
            this.head.f_104203_ = this.rotlerpRad(this.animProgress, 0.43633232F, this.head.f_104203_);
            this.head.f_104204_ = this.rotlerpRad(this.animProgress, 0.0F, this.head.f_104204_);
            this.leftArm.f_104203_ = this.rotlerpRad(this.animProgress, (float) (-Math.PI * 8.0 / 9.0), this.leftArm.f_104203_);
            this.leftArm.f_104204_ = this.rotlerpRad(this.animProgress, (float) (Math.PI / 6), this.leftArm.f_104204_);
         } else if (entityIn.clientAnim == 7) {
            if ((double)this.animProgress >= 0.99) {
               this.leftArm.f_104203_ = Mth.m_14089_(ageInTicks * 0.6662F) * 0.25F;
               this.leftArm.f_104205_ = (float) (-Math.PI * 3.0 / 4.0);
            } else {
               this.leftArm.f_104203_ = this.rotlerpRad(this.animProgress, this.leftArm.f_104203_, Mth.m_14089_(ageInTicks * 0.6662F) * 0.25F);
               this.leftArm.f_104205_ = this.rotlerpRad(this.animProgress, this.leftArm.f_104205_, (float) (-Math.PI * 3.0 / 4.0));
            }
         } else if (entityIn.prevAnim == 7) {
            this.leftArm.f_104203_ = this.rotlerpRad(this.animProgress, Mth.m_14089_(ageInTicks * 0.6662F) * 0.25F, this.leftArm.f_104203_);
            this.leftArm.f_104205_ = this.rotlerpRad(this.animProgress, (float) (-Math.PI * 3.0 / 4.0), this.leftArm.f_104205_);
         }

         this.rightLeg.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
         this.leftLeg.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
         if ((double)this.animProgress >= 0.99) {
            this.rightArm.f_104203_ = this.pose.armX;
            this.rightArm.f_104204_ = this.pose.armY;
            this.rightArm.f_104205_ = this.pose.armZ;
            this.forearm.f_104203_ = this.pose.foreX;
            this.forearm.f_104204_ = this.pose.foreY;
            this.forearm.f_104205_ = this.pose.foreZ;
            this.blade.f_104203_ = this.pose.bladeX;
            this.blade.f_104204_ = this.pose.bladeY;
            this.blade.f_104205_ = this.pose.bladeZ;
         } else {
            this.rightArm.f_104203_ = this.rotlerpRad(this.animProgress, this.prevPose.armX, this.pose.armX);
            this.rightArm.f_104204_ = this.rotlerpRad(this.animProgress, this.prevPose.armY, this.pose.armY);
            this.rightArm.f_104205_ = this.rotlerpRad(this.animProgress, this.prevPose.armZ, this.pose.armZ);
            this.forearm.f_104203_ = this.rotlerpRad(this.animProgress, this.prevPose.foreX, this.pose.foreX);
            this.forearm.f_104204_ = this.rotlerpRad(this.animProgress, this.prevPose.foreY, this.pose.foreY);
            this.forearm.f_104205_ = this.rotlerpRad(this.animProgress, this.prevPose.foreZ, this.pose.foreZ);
            this.blade.f_104203_ = this.rotlerpRad(this.animProgress, this.prevPose.bladeX, this.pose.bladeX);
            this.blade.f_104204_ = this.rotlerpRad(this.animProgress, this.prevPose.bladeY, this.pose.bladeY);
            this.blade.f_104205_ = this.rotlerpRad(this.animProgress, this.prevPose.bladeZ, this.pose.bladeZ);
         }
      } else {
         this.coffin.f_104204_ = netHeadYaw * (float) (Math.PI / 180.0);
      }
   }

   private float rotlerpRad(float progress, float start, float end) {
      float diff = (end - start) % (float) (Math.PI * 2);
      if (diff < (float) -Math.PI) {
         diff += (float) (Math.PI * 2);
      }

      if (diff >= (float) Math.PI) {
         diff -= (float) (Math.PI * 2);
      }

      return start + progress * diff;
   }

   private static class Pose {
      public final float armX;
      public final float armY;
      public final float armZ;
      public final float foreX;
      public final float foreY;
      public final float foreZ;
      public final float bladeX;
      public final float bladeY;
      public final float bladeZ;
      public final boolean fast;

      public Pose(float armX, float armY, float armZ, float foreX, float foreY, float foreZ, float bladeX, float bladeY, float bladeZ) {
         this(armX, armY, armZ, foreX, foreY, foreZ, bladeX, bladeY, bladeZ, true);
      }

      public Pose(float armX, float armY, float armZ, float foreX, float foreY, float foreZ, float bladeX, float bladeY, float bladeZ, boolean fast) {
         this.armX = armX * (float) (-Math.PI / 180.0);
         this.armY = armY * (float) (-Math.PI / 180.0);
         this.armZ = armZ * (float) (Math.PI / 180.0);
         this.foreX = foreX * (float) (-Math.PI / 180.0);
         this.foreY = foreY * (float) (-Math.PI / 180.0);
         this.foreZ = foreZ * (float) (Math.PI / 180.0);
         this.bladeX = bladeX * (float) (-Math.PI / 180.0);
         this.bladeY = bladeY * (float) (-Math.PI / 180.0);
         this.bladeZ = bladeZ * (float) (Math.PI / 180.0);
         this.fast = fast;
      }
   }
}
